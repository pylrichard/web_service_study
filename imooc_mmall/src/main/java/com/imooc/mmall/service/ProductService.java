package com.imooc.mmall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.Product;
import com.imooc.mmall.vo.ProductDetailVo;

public interface ProductService {
    ServerResponse addOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);
}
