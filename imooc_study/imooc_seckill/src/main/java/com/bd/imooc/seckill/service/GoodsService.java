package com.bd.imooc.seckill.service;

import com.bd.imooc.seckill.dao.GoodsDao;
import com.bd.imooc.seckill.domain.SecKillGoods;
import com.bd.imooc.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        SecKillGoods secKillGoods = new SecKillGoods();
        secKillGoods.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(secKillGoods);

        return ret > 0;
    }

    public void resetStock(List<GoodsVo> goodsList) {
        for (GoodsVo goods : goodsList) {
            SecKillGoods secKillGoods = new SecKillGoods();
            secKillGoods.setGoodsId(goods.getId());
            secKillGoods.setStockCount(goods.getStockCount());
            goodsDao.resetStock(secKillGoods);
        }
    }
}
