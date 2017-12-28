package com.bd.roncoo.eshop.one.service.service.fallback;

import com.bd.roncoo.eshop.one.service.service.EshopPriceService;
import org.springframework.stereotype.Component;

@Component
public class EshopPriceServiceFallback implements EshopPriceService {
	@Override
	public String findByProductId(Long productId) {
		return "降级价格数据";
	}
}
