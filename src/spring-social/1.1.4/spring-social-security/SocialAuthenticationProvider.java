package org.springframework.social.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.util.Assert;

/**
 * {@link AuthenticationProvider} for spring-social based {@link ServiceProvider}s
 * 
 * @author Stefan Fussennegger
 * @author Yuan Ji
 */
public class SocialAuthenticationProvider implements AuthenticationProvider {

	private UsersConnectionRepository usersConnectionRepository;
	
	private SocialUserDetailsService userDetailsService;

	public SocialAuthenticationProvider(UsersConnectionRepository usersConnectionRepository, SocialUserDetailsService userDetailsService) {
		this.usersConnectionRepository = usersConnectionRepository;
		this.userDetailsService = userDetailsService;
	}
	
	public boolean supports(Class<? extends Object> authentication) {
		return SocialAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * Authenticate user based on {@link SocialAuthenticationToken}
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(SocialAuthenticationToken.class, authentication, "unsupported authentication type");
		Assert.isTrue(!authentication.isAuthenticated(), "already authenticated");
		SocialAuthenticationToken authToken = (SocialAuthenticationToken) authentication;
		String providerId = authToken.getProviderId();
		Connection<?> connection = authToken.getConnection();

		/*
			connection包含服务提供商信息
		 */
		String userId = toUserId(connection);
		if (userId == null) {
			//第三方登录中不存在对应的用户id，抛出异常，在SocialAuthenticationFilter.doAuthentication()中捕获
			throw new BadCredentialsException("Unknown access token");
		}

		/*
			存在对应的用户id，获取用户信息
		 */
		UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
		if (userDetails == null) {
			throw new UsernameNotFoundException("Unknown connected account id");
		}

		return new SocialAuthenticationToken(connection, userDetails, authToken.getProviderAccountData(), getAuthorities(providerId, userDetails));
	}

	protected String toUserId(Connection<?> connection) {
		//查询业务系统是否存在对应的用户id
		List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
		// only if a single userId is connected to this providerUserId
		return (userIds.size() == 1) ? userIds.iterator().next() : null;
	}

	/**
	 * Override to grant authorities based on {@link ServiceProvider} id and/or a user's account id
	 * @param providerId {@link ServiceProvider} id
	 * @param userDetails {@link UserDetails} as returned by {@link SocialUserDetailsService}
	 * @return extra authorities of this user not already returned by {@link UserDetails#getAuthorities()}
	 */
	protected Collection<? extends GrantedAuthority> getAuthorities(String providerId, UserDetails userDetails) {
		return userDetails.getAuthorities();
	}

}
