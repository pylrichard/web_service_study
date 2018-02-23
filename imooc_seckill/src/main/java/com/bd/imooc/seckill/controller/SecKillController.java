package com.bd.imooc.seckill.controller;

import com.bd.imooc.seckill.access.AccessLimit;
import com.bd.imooc.seckill.domain.SecKillOrder;
import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.rabbitmq.MqSender;
import com.bd.imooc.seckill.rabbitmq.SecKillMessage;
import com.bd.imooc.seckill.redis.GoodsKey;
import com.bd.imooc.seckill.redis.OrderKey;
import com.bd.imooc.seckill.redis.SecKillKey;
import com.bd.imooc.seckill.result.CodeMsg;
import com.bd.imooc.seckill.result.Result;
import com.bd.imooc.seckill.service.*;
import com.bd.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sec_kill")
public class SecKillController implements InitializingBean {
    @Autowired
    SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecKillService secKillService;

    @Autowired
    MqSender sender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSecKillGoodsStock, "" + goods.getId(),
                    goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    @GetMapping("/reset")
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for (GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getSecKillGoodsStock, "" + goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getSecKillOrderByUidGid);
        redisService.delete(SecKillKey.isGoodsOver);
        secKillService.reset(goodsList);

        return Result.success(true);
    }

    @PostMapping("/{path}/sec_kill")
    public Result<Integer> secKill(Model model, SecKillUser user,
                                   @RequestParam("goodsId") long goodsId,
                                   @PathVariable("path") String path) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        /*
            验证path
         */
        boolean check = secKillService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        /*
            内存标记，减少Redis访问次数
         */
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.SEC_KILL_OVER);
        }
        /*
            预减库存
         */
        long stock = redisService.decr(GoodsKey.getSecKillGoodsStock, "" + goodsId);//10
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SEC_KILL_OVER);
        }
        /*
            判断是否已经秒杀成功
         */
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_SEC_KILL);
        }
        //发送消息进行排队
        SecKillMessage mm = new SecKillMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        sender.sendSecKillMessage(mm);

        return Result.success(0);
    }

    @GetMapping("/result")
    public Result<Long> secKillResult(Model model, SecKillUser user,
                                      @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = secKillService.getSecKillResult(user.getId(), goodsId);

        return Result.success(result);
    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping("/path")
    public Result<String> getSecKillPath(HttpServletRequest request, SecKillUser user,
                                         @RequestParam("goodsId") long goodsId,
                                         @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean check = secKillService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = secKillService.createSecKillPath(user, goodsId);

        return Result.success(path);
    }


    @GetMapping("/verify_code")
    public Result<String> getSecKillVerifyCode(HttpServletResponse response, SecKillUser user,
                                               @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = secKillService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return Result.error(CodeMsg.SEC_KILL_FAIL);
        }
    }
}
