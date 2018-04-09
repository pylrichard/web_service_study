package com.bd.roncoo.eshop.one.service.service.fallback;

import com.bd.roncoo.eshop.one.service.service.EshopInventoryService;
import org.springframework.stereotype.Component;

@Component
public class EshopInventoryServiceFallback implements EshopInventoryService {
	@Override
	public String findByProductId(Long productId) {
		return "降级库存数据";
	}
}
