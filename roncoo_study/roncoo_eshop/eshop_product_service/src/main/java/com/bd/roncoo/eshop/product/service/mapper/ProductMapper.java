package com.bd.roncoo.eshop.product.service.mapper;

import com.bd.roncoo.eshop.product.service.model.Product;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductMapper {
	@Insert("INSERT INTO product(name,category_id,brand_id) VALUES(#{name},#{categoryId},#{brandId})")
	void add(Product product);
	
	@Update("UPDATE product SET name=#{name},category_id=#{categoryId},brand_id=#{brandId} WHERE id=#{id}")  
	void update(Product product);
	
	@Delete("DELETE FROM product WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM product WHERE id=#{id}")  
	@Results({
		@Result(column = "category_id", property = "categoryId"),
		@Result(column = "brand_id", property = "brandId")  
	})
	Product findById(Long id);
}
