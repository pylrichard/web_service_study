package com.bd.imooc.seckill.controller;

import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.redis.GoodsKey;
import com.bd.imooc.seckill.result.Result;
import com.bd.imooc.seckill.service.GoodsService;
import com.bd.imooc.seckill.service.RedisService;
import com.bd.imooc.seckill.service.SecKillUserService;
import com.bd.imooc.seckill.vo.GoodsDetailVo;
import com.bd.imooc.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/list", produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model, SecKillUser user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(),
                model.asMap(), applicationContext);
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }

        return html;
    }

    @RequestMapping(value = "/detail_html/{goodsId}", produces = "text/html")
    public String getGoodDetailPage(HttpServletRequest request, HttpServletResponse response,
                                    Model model, SecKillUser user,
                                    @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        SecKillStatus secKillStatus = createSecKillStatus(startAt, endAt);
        model.addAttribute("secKillStatus", secKillStatus.getSecKillStatus());
        model.addAttribute("remainSeconds", secKillStatus.getRemainSeconds());
        SpringWebContext ctx = new SpringWebContext(request, response,
                request.getServletContext(), request.getLocale(),
                model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }

        return html;
    }

    @RequestMapping(value = "/detail_data/{goodsId}")
    public Result<GoodsDetailVo> getGoodDetailData(HttpServletRequest request, HttpServletResponse response,
                                                   Model model, SecKillUser user,
                                                   @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        SecKillStatus secKillStatus = createSecKillStatus(startAt, endAt);
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(secKillStatus.getRemainSeconds());
        vo.setSecKillStatus(secKillStatus.getSecKillStatus());

        return Result.success(vo);
    }

    private SecKillStatus createSecKillStatus(long startAt, long endAt) {
        long now = System.currentTimeMillis();
        SecKillStatus secKillStatus = new SecKillStatus();
        if (now < startAt) {
            //秒杀还没开始，倒计时
            secKillStatus.setSecKillStatus(0);
            secKillStatus.setRemainSeconds((int) ((startAt - now) / 1000));
        } else if (now > endAt) {
            //秒杀已经结束
            secKillStatus.setSecKillStatus(2);
            secKillStatus.setRemainSeconds(-1);
        } else {
            //秒杀进行中
            secKillStatus.setSecKillStatus(1);
            secKillStatus.setRemainSeconds(0);
        }

        return secKillStatus;
    }
}
