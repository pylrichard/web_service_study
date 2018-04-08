# Spring Security授权流程分析

```java
FilterSecurityInterceptor.doFilter()
	FilterSecurityInterceptor.invoke()
  		AbstractSecurityInterceptor.beforeInvocation()
  			DefaultFilterInvocationSecurityMetadataSource.getAttributes()
  			AbstractSecurityInterceptor.authenticateIfRequired()
  			AffirmativeBased.decide()
  				WebExpressionVoter.vote()
  		AbstractSecurityInterceptor.beforeInvocation()
  		ExceptionTranslationFilter.doFilter()
  			ExceptionTranslationFilter.handleSpringSecurityException()
```