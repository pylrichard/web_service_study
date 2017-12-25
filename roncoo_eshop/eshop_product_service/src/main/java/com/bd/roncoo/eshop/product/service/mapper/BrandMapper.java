package com.bd.roncoo.eshop.product.service.mapper;

import com.bd.roncoo.eshop.product.service.model.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BrandMapper {
	@Insert("INSERT INTO brand(name,description) VALUES(#{name},#{description})")
	void add(Brand brand);
	
	@Update("UPDATE brand SET name=#{name},description=#{description} WHERE id=#{id}")  
	void update(Brand brand);
	
	@Delete("DELETE FROM brand WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM brand WHERE id=#{id}")  
	Brand findById(Long id);
	
	@Select("SELECT * FROM brand WHERE id in (${ids})")
	List<Brand> findByIds(@Param("ids") String ids);
}
