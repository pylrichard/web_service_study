package com.bd.roncoo.eshop.product.service.mapper;

import com.bd.roncoo.eshop.product.service.model.ProductIntro;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductIntroMapper {
	@Insert("INSERT INTO product_intro(content,product_id) VALUES(#{content},#{productId})")
	void add(ProductIntro productIntro);
	
	@Update("UPDATE product_intro SET content=#{content},product_id=#{productId} WHERE id=#{id}")  
	void update(ProductIntro productIntro);
	
	@Delete("DELETE FROM product_intro WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM product_intro WHERE id=#{id}")  
	ProductIntro findById(Long id);
}
