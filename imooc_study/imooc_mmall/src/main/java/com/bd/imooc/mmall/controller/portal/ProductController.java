package com.bd.imooc.mmall.controller.portal;

import com.bd.imooc.mmall.common.ServerResponse;
import com.bd.imooc.mmall.service.ProductService;
import com.bd.imooc.mmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return productService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false)String keyword,
                                         @RequestParam(value = "categoryId", required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1")int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10")int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "")String orderBy) {
        return productService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
