package org.springframework.security.web.access.intercept;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Default implementation of <tt>FilterInvocationDefinitionSource</tt>.
 * <p>
 * Stores an ordered map of {@link RequestMatcher}s to <tt>ConfigAttribute</tt>
 * collections and provides matching of {@code FilterInvocation}s against the items stored
 * in the map.
 * <p>
 * The order of the {@link RequestMatcher}s in the map is very important. The <b>first</b>
 * one which matches the request will be used. Later matchers in the map will not be
 * invoked if a match has already been found. Accordingly, the most specific matchers
 * should be registered first, with the most general matches registered last.
 * <p>
 * The most common method creating an instance is using the Spring Security namespace. For
 * example, the {@code pattern} and {@code access} attributes of the
 * {@code <intercept-url>} elements defined as children of the {@code <http>}
 * element are combined to build the instance used by the
 * {@code FilterSecurityInterceptor}.
 *
 * @author Ben Alex
 * @author Luke Taylor
 */
public class DefaultFilterInvocationSecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * AbstractChannelSecurityConfig子类的HttpSecurity.authorizeRequests()配置的URL集合
     */
    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    // ~ Constructors
    // ===================================================================================================

    /**
     * Sets the internal request map from the supplied map. The key elements should be of
     * type {@link RequestMatcher}, which. The path stored in the key will depend on the
     * type of the supplied UrlMatcher.
     *
     * @param requestMap order-preserving map of request definitions to attribute lists
     */
    public DefaultFilterInvocationSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {

        this.requestMap = requestMap;
    }

    // ~ Methods
    // ========================================================================================================

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    public Collection<ConfigAttribute> getAttributes(Object object) {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            //匹配请求的权限规则，是不需要认证还是需要认证
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
