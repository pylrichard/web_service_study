package com.bd.ace.trade.common.client;

import com.bd.ace.trade.common.constant.TradeEnums;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RestClientProxyFactory implements FactoryBean {
	private static RestTemplate restTemplate = new RestTemplate();
	/**
	 * 要调用的服务接口
	 */
	private Class serviceInterface;
	private TradeEnums.RestServerEnum serverEnum;

	public Class getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(Class serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	/**
	 * 获取代理对象
	 */
	@Override
	public Object getObject() throws Exception {
		return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceInterface}, new ClientProxy());
	}

	@Override
	public Class getObjectType() {
		return serviceInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public static void setRestTemplate(RestTemplate restTemplate) {
		RestClientProxyFactory.restTemplate = restTemplate;
	}

	public void setServerEnum(TradeEnums.RestServerEnum serverEnum) {
		this.serverEnum = serverEnum;
	}

	private class ClientProxy implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return restTemplate.postForObject(serverEnum.getServerUrl() + method.getName(),
											args[0], method.getReturnType());
		}
	}
}
