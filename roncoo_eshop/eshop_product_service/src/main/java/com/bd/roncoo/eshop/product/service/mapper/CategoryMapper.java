package com.bd.roncoo.eshop.product.service.mapper;

import com.bd.roncoo.eshop.product.service.model.Category;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CategoryMapper {
	@Insert("INSERT INTO category(name,description) VALUES(#{name},#{description})")
	void add(Category category);
	
	@Update("UPDATE category SET name=#{name},description=#{description} WHERE id=#{id}")  
	void update(Category category);
	
	@Delete("DELETE FROM category WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM category WHERE id=#{id}")  
	Category findById(Long id);
}
