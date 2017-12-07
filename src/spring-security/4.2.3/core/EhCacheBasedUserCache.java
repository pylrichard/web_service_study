package org.springframework.security.core.userdetails.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class EhCacheBasedUserCache implements UserCache, InitializingBean {
	// ~ Static fields/initializers
	// =====================================================================================

	private static final Log logger = LogFactory.getLog(EhCacheBasedUserCache.class);

	// ~ Instance fields
	// ================================================================================================

	private Ehcache cache;

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "cache mandatory");
	}

	public Ehcache getCache() {
		return cache;
	}

	public UserDetails getUserFromCache(String username) {
		Element element = cache.get(username);

		if (logger.isDebugEnabled()) {
			logger.debug("Cache hit: " + (element != null) + "; username: " + username);
		}

		if (element == null) {
			return null;
		}
		else {
			return (UserDetails) element.getValue();
		}
	}

	public void putUserInCache(UserDetails user) {
		Element element = new Element(user.getUsername(), user);

		if (logger.isDebugEnabled()) {
			logger.debug("Cache put: " + element.getKey());
		}

		cache.put(element);
	}

	public void removeUserFromCache(UserDetails user) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + user.getUsername());
		}

		this.removeUserFromCache(user.getUsername());
	}

	public void removeUserFromCache(String username) {
		cache.remove(username);
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}
}
