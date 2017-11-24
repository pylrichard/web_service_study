# OAuth2流程源码分析

```java
SocialAuthenticationFilter.attemptAuthentication()
	SocialAuthenticationFilter.attemptAuthService()
		OAuth2AuthenticationService.getAuthToken()
  			OAuth2Template.exchangeForAccess()
```