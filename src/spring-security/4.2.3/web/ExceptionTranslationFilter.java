package org.springframework.security.web.access;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles any <code>AccessDeniedException</code> and <code>AuthenticationException</code>
 * thrown within the filter chain.
 * <p>
 * This filter is necessary because it provides the bridge between Java exceptions and
 * HTTP responses. It is solely concerned with maintaining the user interface. This filter
 * does not do any actual security enforcement.
 * <p>
 * If an {@link AuthenticationException} is detected, the filter will launch the
 * <code>authenticationEntryPoint</code>. This allows common handling of authentication
 * failures originating from any subclass of
 * {@link org.springframework.security.access.intercept.AbstractSecurityInterceptor}.
 * <p>
 * If an {@link AccessDeniedException} is detected, the filter will determine whether or
 * not the user is an anonymous user. If they are an anonymous user, the
 * <code>authenticationEntryPoint</code> will be launched. If they are not an anonymous
 * user, the filter will delegate to the
 * {@link org.springframework.security.web.access.AccessDeniedHandler}. By default the
 * filter will use {@link org.springframework.security.web.access.AccessDeniedHandlerImpl}.
 * <p>
 * To use this filter, it is necessary to specify the following properties:
 * <ul>
 * <li><code>authenticationEntryPoint</code> indicates the handler that should commence
 * the authentication process if an <code>AuthenticationException</code> is detected. Note
 * that this may also switch the current protocol from http to https for an SSL login.</li>
 * <li><tt>requestCache</tt> determines the strategy used to save a request during the
 * authentication process in order that it may be retrieved and reused once the user has
 * authenticated. The default implementation is {@link HttpSessionRequestCache}.</li>
 * </ul>
 *
 * @author Ben Alex
 * @author colin sampaleanu
 */
public class ExceptionTranslationFilter extends GenericFilterBean {

	// ~ Instance fields
	// ================================================================================================

	private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
	private AuthenticationEntryPoint authenticationEntryPoint;
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	private RequestCache requestCache = new HttpSessionRequestCache();

	public ExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
		this(authenticationEntryPoint, new HttpSessionRequestCache());
	}

	public ExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint,
			RequestCache requestCache) {
		Assert.notNull(authenticationEntryPoint,
				"authenticationEntryPoint cannot be null");
		Assert.notNull(requestCache, "requestCache cannot be null");
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.requestCache = requestCache;
	}

	// ~ Methods
	// ========================================================================================================

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(authenticationEntryPoint,
				"authenticationEntryPoint must be specified");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		try {
			chain.doFilter(request, response);

			logger.debug("Chain processed normally");
		}
		catch (IOException ex) {
			throw ex;
		}
		catch (Exception ex) {
			// Try to extract a SpringSecurityException from the stacktrace
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer
					.getFirstThrowableOfType(AuthenticationException.class, causeChain);

			if (ase == null) {
			    //判断根异常是否是AccessDeniedException
				ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(
						AccessDeniedException.class, causeChain);
			}

			if (ase != null) {
			    /*
			    	根据不同异常进行不同处理
			    	比如用户未登录，会跳转到登录页面进行登录，发起/login请求
			    	输入用户名和密码后，到UsernamePasswordAuthenticationFilter进行处理


			     */
				handleSpringSecurityException(request, response, chain, ase);
			}
			else {
				// Rethrow ServletExceptions and RuntimeExceptions as-is
				if (ex instanceof ServletException) {
					throw (ServletException) ex;
				}
				else if (ex instanceof RuntimeException) {
					throw (RuntimeException) ex;
				}

				// Wrap other Exceptions. This shouldn't actually happen
				// as we've already covered all the possibilities for doFilter
				throw new RuntimeException(ex);
			}
		}
	}

	public AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	protected AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return authenticationTrustResolver;
	}

	private void handleSpringSecurityException(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, RuntimeException exception)
			throws IOException, ServletException {
		if (exception instanceof AuthenticationException) {
			logger.debug(
					"Authentication exception occurred; redirecting to authentication entry point",
					exception);

			sendStartAuthentication(request, response, chain,
					(AuthenticationException) exception);
		}
		else if (exception instanceof AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
				logger.debug(
						"Access is denied (user is " + (authenticationTrustResolver.isAnonymous(authentication) ? "anonymous" : "not fully authenticated") + "); redirecting to authentication entry point",
						exception);

				//是匿名登录，引导进行身份认证
				sendStartAuthentication(
						request,
						response,
						chain,
						new InsufficientAuthenticationException(
								"Full authentication is required to access this resource"));
			}
			else {
				logger.debug(
						"Access is denied (user is not anonymous); delegating to AccessDeniedHandler",
						exception);

				//返回403拒绝访问
				accessDeniedHandler.handle(request, response,
						(AccessDeniedException) exception);
			}
		}
	}

	protected void sendStartAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			AuthenticationException reason) throws ServletException, IOException {
		// SEC-112: Clear the SecurityContextHolder's Authentication, as the
		// existing Authentication is no longer considered valid
		SecurityContextHolder.getContext().setAuthentication(null);
		requestCache.saveRequest(request, response);
		logger.debug("Calling Authentication entry point.");
		//跳转到WebSecurityConfigurerAdapter子类HttpSecurity.loginPage()配置的URL
		authenticationEntryPoint.commence(request, response, reason);
	}

	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		Assert.notNull(accessDeniedHandler, "AccessDeniedHandler required");
		this.accessDeniedHandler = accessDeniedHandler;
	}

	public void setAuthenticationTrustResolver(
			AuthenticationTrustResolver authenticationTrustResolver) {
		Assert.notNull(authenticationTrustResolver,
				"authenticationTrustResolver must not be null");
		this.authenticationTrustResolver = authenticationTrustResolver;
	}

	public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
		Assert.notNull(throwableAnalyzer, "throwableAnalyzer must not be null");
		this.throwableAnalyzer = throwableAnalyzer;
	}

	/**
	 * Default implementation of <code>ThrowableAnalyzer</code> which is capable of also
	 * unwrapping <code>ServletException</code>s.
	 */
	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		/**
		 * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
		 */
		protected void initExtractorMap() {
			super.initExtractorMap();

			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable,
							ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}

	}

}
