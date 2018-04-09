package com.bd.roncoo.eshop.product.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductProperty {
	private Long id;
	private String name;
	private String value;
	private Long productId;
}
