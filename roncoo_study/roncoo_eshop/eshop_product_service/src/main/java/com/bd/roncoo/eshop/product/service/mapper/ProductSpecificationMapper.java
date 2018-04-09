package com.bd.roncoo.eshop.product.service.mapper;

import com.bd.roncoo.eshop.product.service.model.ProductSpecification;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductSpecificationMapper {
	@Insert("INSERT INTO product_specification(name,value,product_id) VALUES(#{name},#{value},#{productId})")
	void add(ProductSpecification productSpecification);
	
	@Update("UPDATE product_specification SET name=#{name},value=#{value},product_id=#{productId} WHERE id=#{id}")  
	void update(ProductSpecification productSpecification);
	
	@Delete("DELETE FROM product_specification WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM product_specification WHERE id=#{id}")  
	ProductSpecification findById(Long id);
	
	@Select("SELECT * FROM product_specification WHERE product_id=#{productId}")  
	ProductSpecification findByProductId(Long productId);
}
