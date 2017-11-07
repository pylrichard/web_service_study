package com.imooc.mmall.service;

import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.Product;

public interface ProductService {
    ServerResponse addOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
}
