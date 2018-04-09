package com.bd.roncoo.eshop.product.service.mapper;

import com.bd.roncoo.eshop.product.service.model.ProductProperty;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductPropertyMapper {
	@Insert("INSERT INTO product_property(name,value,product_id) VALUES(#{name},#{value},#{productId})")
	void add(ProductProperty productProperty);
	
	@Update("UPDATE product_property SET name=#{name},value=#{value},product_id=#{productId} WHERE id=#{id}")  
	void update(ProductProperty productProperty);
	
	@Delete("DELETE FROM product_property WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM product_property WHERE id=#{id}")  
	ProductProperty findById(Long id);
	
	@Select("SELECT * FROM product_property WHERE product_id=#{productId}")  
	ProductProperty findByProductId(Long productId);
}
