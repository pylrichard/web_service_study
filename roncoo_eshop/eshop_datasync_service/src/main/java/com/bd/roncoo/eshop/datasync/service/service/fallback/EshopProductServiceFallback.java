package com.bd.roncoo.eshop.datasync.service.service.fallback;

import com.bd.roncoo.eshop.datasync.service.service.EshopProductService;
import org.springframework.stereotype.Component;

/**
 * Spring Cloud里使用Feign调用服务API，将Hystrix跟Feign整合对后端服务API做资源隔离
 * 某一个服务不可用会自动降级，不至于导致整个服务集群不可用
 * Hystrix还可以自动进行限流
 */
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
