package com.bd.roncoo.eshop.price.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPrice {
	private Long id;
	private Double value;
	private Long productId;
}
