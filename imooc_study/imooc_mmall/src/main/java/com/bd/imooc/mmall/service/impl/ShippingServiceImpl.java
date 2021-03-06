package com.bd.imooc.mmall.service.impl;

import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.dao.ShippingMapper;
import com.bd.imooc.mmall.pojo.Shipping;
import com.bd.imooc.mmall.service.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse addShipping(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            /*
                在ShippingMapper.xml.insert中添加useGeneratedKeys="true" keyProperty="id"
                自增主键值会自动填充到Shipping.id字段，返回给前端
             */
            result.put("shippingId", shipping.getId());

            return ServerResponse.createBySuccess("新建地址成功", result);
        }

        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse<String> deleteShipping(Integer userId, Integer shippingId) {
        //绑定用户id和地址id，避免横向越权
        int rowCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("删除地址成功");
        }

        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse updateShipping(Integer userId, Shipping shipping) {
        //设置为登录用户id，避免横向越权
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新地址成功");
        }

        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> selectShipping(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if (shipping == null) {
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }

        return ServerResponse.createBySuccess("查询地址成功", shipping);
    }

    @Override
    public ServerResponse<PageInfo> getShippingList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);

        return ServerResponse.createBySuccess(pageInfo);
    }
}
