package com.bd.imooc.seckill.dao;

import com.bd.imooc.seckill.domain.OrderInfo;
import com.bd.imooc.seckill.domain.SecKillOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {
    @Select("select * from sec_kill_order where user_id = #{userId} and goods_id = #{goodsId}")
    SecKillOrder getSecKillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date) values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into sec_kill_order(user_id, goods_id, order_id) values(#{userId}, #{goodsId}, #{orderId})")
    int insertSecKillOrder(SecKillOrder seckillOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

    @Delete("delete from sec_kill_order")
    void deleteSecKillOrders();
}
