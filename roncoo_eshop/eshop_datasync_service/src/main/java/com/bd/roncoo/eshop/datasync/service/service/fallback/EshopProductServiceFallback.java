package com.bd.roncoo.eshop.datasync.service.service.fallback;

import com.bd.roncoo.eshop.datasync.service.service.EshopProductService;
import org.springframework.stereotype.Component;

@Component
public class EshopProductServiceFallback implements EshopProductService {
	@Override
	public String findBrandById(Long id) {
		return null;
	}

	@Override
	public String findBrandByIds(String ids) {
		return null;
	}

	@Override
	public String findCategoryById(Long id) {
		return null;
	}

	@Override
	public String findProductIntroById(Long id) {
		return null;
	}

	@Override
	public String findProductPropertyById(Long id) {
		return null;
	}

	@Override
	public String findProductById(Long id) {
		return null;
	}

	@Override
	public String findProductSpecificationById(Long id) {
		return null;
	}
}
