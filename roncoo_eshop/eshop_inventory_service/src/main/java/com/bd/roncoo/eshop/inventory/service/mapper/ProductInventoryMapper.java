package com.bd.roncoo.eshop.inventory.service.mapper;

import com.bd.roncoo.eshop.inventory.service.model.ProductInventory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductInventoryMapper {
    @Insert("INSERT INTO product_inventory(inventory_cnt, product_id) VALUES(#{inventoryCnt}, #{productId})")
    void add(ProductInventory productInventory);

    @Update("UPDATE product_inventory SET inventory_cnt=#{inventoryCnt}, product_id=#{productId} WHERE id=#{id}")
    void update(ProductInventory productInventory);
	
	@Delete("DELETE FROM product_inventory WHERE id=#{id}")  
	void delete(Long id);
	
	@Select("SELECT * FROM product_inventory WHERE id=#{id}")  
	ProductInventory findById(Long id);
	
	@Select("SELECT * FROM product_inventory WHERE product_id=#{productId}")  
	ProductInventory findByProductId(Long productId);
}
