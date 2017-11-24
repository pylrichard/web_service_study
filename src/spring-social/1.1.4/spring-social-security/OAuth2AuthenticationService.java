package org.springframework.social.security.provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stefan Fussennegger
 * @param <S> The provider's API type.
 */
public class OAuth2AuthenticationService<S> extends AbstractSocialAuthenticationService<S> {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private OAuth2ConnectionFactory<S> connectionFactory;

	private Set<String> returnToUrlParameters;
	
	private String defaultScope = "";
	
	public OAuth2AuthenticationService(OAuth2ConnectionFactory<S> connectionFactory) {
		setConnectionFactory(connectionFactory);
	}
	
	public OAuth2ConnectionFactory<S> getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(OAuth2ConnectionFactory<S> connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void setReturnToUrlParameters(Set<String> returnToUrlParameters) {
		Assert.notNull(returnToUrlParameters, "returnToUrlParameters cannot be null");
		this.returnToUrlParameters = returnToUrlParameters;
	}

	public Set<String> getReturnToUrlParameters() {
		if (returnToUrlParameters == null) {
			returnToUrlParameters = new HashSet<String>();
		}
		return returnToUrlParameters;
	}

	/**
	 * @param defaultScope OAuth scope to use, i.e. requested permissions
	 */
	public void setDefaultScope(String defaultScope) {
		this.defaultScope = defaultScope;
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(getConnectionFactory(), "connectionFactory");
	}

	public SocialAuthenticationToken getAuthToken(HttpServletRequest request, HttpServletResponse response) throws SocialAuthenticationRedirectException {
		String code = request.getParameter("code");
		/*
			SocialAuthenticationFilter即处理授权登录请求，也处理授权成功返回授权码的请求
		 */
		if (!StringUtils.hasText(code)) {
			/*
				请求没有授权码，则是授权登录请求
			 */
			OAuth2Parameters params =  new OAuth2Parameters();
			params.setRedirectUri(buildReturnToUrl(request));
			setScope(request, params);
			params.add("state", generateState(connectionFactory, request));
			addCustomParameters(params);
			//重定向引导用户到认证服务器进行授权
			throw new SocialAuthenticationRedirectException(getConnectionFactory().getOAuthOperations().buildAuthenticateUrl(params));
		} else if (StringUtils.hasText(code)) {
			/*
				请求中包含授权码，则是授权成功后返回授权码的请求
			 */
			try {
				String returnToUrl = buildReturnToUrl(request);
				//用授权码获取AccessToken，调用OAuth2Template.exchangeForAccess()
				AccessGrant accessGrant = getConnectionFactory().getOAuthOperations().exchangeForAccess(code, returnToUrl, null);
				// TODO avoid API call if possible (auth using token would be fine)
				Connection<S> connection = getConnectionFactory().createConnection(accessGrant);
				return new SocialAuthenticationToken(connection, null);
			} catch (RestClientException e) {
				logger.debug("failed to exchange for access", e);
				//OAuth2Template.postForAccessGrant()返回的响应是text/html，触发异常在此捕获
				return null;
			}
		} else {
			return null;
		}
	}

	private String generateState(OAuth2ConnectionFactory<?> connectionFactory, HttpServletRequest request) {
	    final String state = request.getParameter("state");
	    return (state != null) ? state : connectionFactory.generateState();
	}

	protected String buildReturnToUrl(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();
		sb.append("?");
		for (String name : getReturnToUrlParameters()) {
			// Assume for simplicity that there is only one value
			String value = request.getParameter(name);

			if (value == null) {
				continue;
			}
			sb.append(name).append("=").append(value).append("&");
		}
		sb.setLength(sb.length() - 1); // strip trailing ? or &
		return sb.toString();
	}

	private void setScope(HttpServletRequest request, OAuth2Parameters params) {
		String requestedScope = request.getParameter("scope");
		if (StringUtils.hasLength(requestedScope)) {
			params.setScope(requestedScope);
		} else if (StringUtils.hasLength(defaultScope)) {
			params.setScope(defaultScope);
		}
	}

	protected void addCustomParameters(OAuth2Parameters params) {
	}
}
