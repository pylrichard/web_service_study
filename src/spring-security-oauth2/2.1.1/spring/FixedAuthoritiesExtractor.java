package org.springframework.boot.autoconfigure.security.oauth2.resource;

public class FixedAuthoritiesExtractor implements AuthoritiesExtractor {
	private static final String AUTHORITIES = "authorities";

	private static final String[] AUTHORITY_KEYS = { "authority", "role", "value" };

	@Override
	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        //默认权限
		String authorities = "ROLE_USER";
        //JSON信息包含authorities属性，则权限为对应的值
        //否则为默认值，比如GitHub返回的JSON信息没有authorities属性
        //比如QQ登录拿到OpenID后，到数据库中查询相应权限，赋值给authorities
		if (map.containsKey(AUTHORITIES)) {
			authorities = asAuthorities(map.get(AUTHORITIES));
		}
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}
}
