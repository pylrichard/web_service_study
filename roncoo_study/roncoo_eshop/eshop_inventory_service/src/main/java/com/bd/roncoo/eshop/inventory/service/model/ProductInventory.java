package com.bd.roncoo.eshop.inventory.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductInventory {
	private Long id;
	private Long productId;
    private Long inventoryCnt;

    public ProductInventory(Long productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }
}
