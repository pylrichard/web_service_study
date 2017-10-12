package org.springframework.boot.autoconfigure.security.oauth2.resource;

public class FixedPrincipalExtractor implements PrincipalExtractor {
    //比如GitHub返回的JSON中包含username，将对应的值放入Principal
	private static final String[] PRINCIPAL_KEYS = new String[] { "user", "username",
			"userid", "user_id", "login", "id", "name" };

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
		for (String key : PRINCIPAL_KEYS) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
		}
		return null;
	}

}
