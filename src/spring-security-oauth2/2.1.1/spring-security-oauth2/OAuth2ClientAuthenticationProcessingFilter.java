package org.springframework.security.oauth2.client.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

/**
 * An OAuth2 client filter that can be used to acquire an OAuth2 access token from an authorization server, and load an
 * authentication object into the SecurityContext
 * 
 * @author Vidya Valmikinathan
 * 
 */
public class OAuth2ClientAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	public OAuth2RestOperations restTemplate;

	private ResourceServerTokenServices tokenServices;

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();

	private ApplicationEventPublisher eventPublisher;

	/**
	 * Reference to a CheckTokenServices that can validate an OAuth2AccessToken
	 * 
	 * @param tokenServices
	 */
	public void setTokenServices(ResourceServerTokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}

	/**
	 * A rest template to be used to obtain an access token.
	 * 
	 * @param restTemplate a rest template
	 */
	public void setRestTemplate(OAuth2RestOperations restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
		super.setApplicationEventPublisher(eventPublisher);
	}
	
	public OAuth2ClientAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(new NoopAuthenticationManager());
		setAuthenticationDetailsSource(authenticationDetailsSource);
	}

	@Override
	public void afterPropertiesSet() {
		Assert.state(restTemplate != null, "Supply a rest-template");
		super.afterPropertiesSet();
	}

	//在AbstractAuthenticationProcessingFilter.doFilter中调用
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		OAuth2AccessToken accessToken;
		try {
			//处理localhost:8080/login这个请求，尝试获取AccessToken，见OAuth2RestTemplate.getAccessToken
			//
			//授权服务器返回AccessToken
			accessToken = restTemplate.getAccessToken();
		} catch (OAuth2Exception e) {
			BadCredentialsException bad = new BadCredentialsException("Could not obtain access token", e);
			publish(new OAuth2AuthenticationFailureEvent(bad));
			throw bad;			
		}
		try {
			//通过AccessToken去授权服务器获取用户信息
			//调用org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices，见UserInfoTokenServices.java
			//将OAuth2Authentication放入SecurityContext中，见AbstractAuthenticationProcessingFilter.successfulAuthentication
			OAuth2Authentication result = tokenServices.loadAuthentication(accessToken.getValue());
			if (authenticationDetailsSource!=null) {
				request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, accessToken.getValue());
				request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, accessToken.getTokenType());
				result.setDetails(authenticationDetailsSource.buildDetails(request));
			}
			publish(new AuthenticationSuccessEvent(result));
			return result;
		}
		catch (InvalidTokenException e) {
			BadCredentialsException bad = new BadCredentialsException("Could not obtain user details from token", e);
			publish(new OAuth2AuthenticationFailureEvent(bad));
			throw bad;			
		}

	}

	private void publish(ApplicationEvent event) {
		if (eventPublisher!=null) {
			eventPublisher.publishEvent(event);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		// Nearly a no-op, but if there is a ClientTokenServices then the token will now be stored
		restTemplate.getAccessToken();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		if (failed instanceof AccessTokenRequiredException) {
			// Need to force a redirect via the OAuth client filter, so rethrow here
			throw failed;
		}
		else {
			// If the exception is not a Spring Security exception this will result in a default error page
			super.unsuccessfulAuthentication(request, response, failed);
		}
	}
	
	private static class NoopAuthenticationManager implements AuthenticationManager {

		@Override
		public Authentication authenticate(Authentication authentication)
				throws AuthenticationException {
			throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
		}
		
	}

}
