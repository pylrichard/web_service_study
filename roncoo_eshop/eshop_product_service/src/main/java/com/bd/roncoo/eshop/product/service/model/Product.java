package com.bd.roncoo.eshop.product.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private Long id;
	private String name;
	private Long categoryId;
	private Long brandId;
}
