package org.springframework.boot.autoconfigure.security.oauth2.resource;

public class UserInfoTokenServices implements ResourceServerTokenServices {
	protected final Log logger = LogFactory.getLog(getClass());

	private final String userInfoEndpointUrl;

	private final String clientId;

	private OAuth2RestOperations restTemplate;

	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

	private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken)
			throws AuthenticationException, InvalidTokenException {
        //发送GET请求，获取用户信息
		Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);
		if (map.containsKey("error")) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("userinfo returned error: " + map.get("error"));
			}
			throw new InvalidTokenException(accessToken);
		}
		return extractAuthentication(map);
	}

	private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        //获取Principal
		Object principal = getPrincipal(map);
        //获取用户对应的权限信息
		List<GrantedAuthority> authorities = this.authoritiesExtractor
				.extractAuthorities(map);
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null,
				null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				principal, "N/A", authorities);
		token.setDetails(map);
        //将获取的JSON格式的用户信息转换为OAuth2Authentication
		return new OAuth2Authentication(request, token);
	}

	protected Object getPrincipal(Map<String, Object> map) {
        //默认使用FixedPrincipalExtractor
        //需要自定义实现BeanPostProcessor子类，在postProcessAfterInitialization中修改
		Object principal = this.principalExtractor.extractPrincipal(map);
		return (principal == null ? "unknown" : principal);
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> getMap(String path, String accessToken) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Getting user info from: " + path);
		}
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
					.getAccessToken();
			if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
						accessToken);
				token.setTokenType(this.tokenType);
				restTemplate.getOAuth2ClientContext().setAccessToken(token);
			}
            //path就是application.properties中的配置项security.oauth2.resource.user-info-uri
            //如https://api.github.com/user
            //获取到的用户信息是JSON格式
			return restTemplate.getForEntity(path, Map.class).getBody();
		}
		catch (Exception ex) {
			this.logger.warn("Could not fetch user details: " + ex.getClass() + ", "
					+ ex.getMessage());
			return Collections.<String, Object>singletonMap("error",
					"Could not fetch user details");
		}
	}
}
