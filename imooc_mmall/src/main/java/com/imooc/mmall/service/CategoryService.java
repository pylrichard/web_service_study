package com.imooc.mmall.service;

import com.imooc.mmall.common.ServerResponse;

public interface CategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse setCategoryName(Integer categoryId, String categoryName);
}
