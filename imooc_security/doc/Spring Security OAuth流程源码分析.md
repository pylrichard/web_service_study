# Spring Security OAuth流程源码分析

```java
TokenEndpoint.postAccessToken()
	InMemoryClientDetailsService.loadClientByClientId()
	DefaultOAuth2RequestFactory.createTokenRequest()
  	CompositeTokenGranter.grant()
  		AbstractTokenGranter.grant()
  			AbstractTokenGranter.getAccessToken()
  				ResourceOwnerPasswordTokenGranter.getOAuth2Authentication()
  				DefaultTokenServices.createAccessToken()
```

