package com.bd.imooc.mmall.service;

import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.pojo.Product;
import com.bd.imooc.mmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    ServerResponse addOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
                                                         int pageNum, int pageSize, String orderBy);
}
