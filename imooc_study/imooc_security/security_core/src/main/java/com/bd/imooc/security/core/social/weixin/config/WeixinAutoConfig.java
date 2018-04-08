package com.bd.imooc.security.core.social.weixin.config;

import com.bd.imooc.security.core.properties.SecurityProperties;
import com.bd.imooc.security.core.properties.WeixinProperties;
import com.bd.imooc.security.core.social.view.ImoocConnectView;
import com.bd.imooc.security.core.social.view.ImoocConnectionStatusView;
import com.bd.imooc.security.core.social.weixin.api.Weixin;
import com.bd.imooc.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.web.servlet.View;

@Configuration
//application.properties中配置了imooc.security.social.weixin.app-id，以下配置才会生效
@ConditionalOnProperty(prefix = "imooc.security.social.weixin", name = "app-id")
@ConditionalOnClass({SocialConfigurerAdapter.class, WeixinConnectionFactory.class})
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class WeixinAutoConfig extends SocialAutoConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Weixin getWeixin(ConnectionRepository repository) {
		Connection<Weixin> connection = repository.findPrimaryConnection(Weixin.class);
		return connection != null ? connection.getApi() : null;
	}

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

	/**
	 * GenericConnectionStatusView.generateConnectionViewHtml()渲染得到HTML页面
	 * ConnectController.connectionStatus()往参数model中添加属性，如connections
	 * getProfileIfConnected()通过参数model得到属性connections
	 * 需要设置spring.social.auto-connection-views = true
	 *
	 * GenericConnectionStatusView生成的页面中点击Connect to WeiXin按钮
	 * 发送POST请求
	 * public RedirectView connect(@PathVariable String providerId, NativeWebRequest request)中处理
	 *
	 * getProfileIfConnected()通过调用connection.fetchUserProfile()
	 * 即调用AbstractConnection在WeixinAdapter中获取微信用户信息
	 *
	 * connect/weixinConnect见protected String connectView(String providerId)
	 * connect/weixinConnected见protected String connectedView(String providerId)
	 *
	 * 应用可提供自定义实现覆盖此视图
	 */
	@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new ImoocConnectView();
	}

	/**
	 * org.springframework.social.connect.web.ConnectController
	 * <p>
	 * /connect请求会在
	 * public String connectionStatus(NativeWebRequest request, Model model)中处理
	 * 最后调用connectView()返回String，如connect/status表示视图名称，视图会展示为HTML页面
	 * <p>
	 * 获取微信绑定信息往/connect/weixin发送GET请求
	 * 执行微信绑定往/connect/weixin发送POST请求
	 * 解除微信绑定往/connect/weixin发送DELETE请求，请求执行成功后再次会发送GET请求
	 * <p>
	 * 获取所有第三方应用绑定信息，往/connect发送GET请求
	 */
	@Bean(name = {"connect/status"})
	@ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
	public View connectStatusView() {
		return new ImoocConnectionStatusView();
	}
}
