package com.bd.roncoo.eshop.inventory.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInventory {
	private Long id;
	private Integer value;
	private Long productId;
}
