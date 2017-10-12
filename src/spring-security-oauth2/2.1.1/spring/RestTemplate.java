package org.springframework.web.client;

public class RestTemplate extends InterceptingHttpAccessor implements RestOperations {
	@Override
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
		throws RestClientException {
		RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
		ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
		return execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
	}

    @Override
	public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
		ResponseExtractor<T> responseExtractor, Map<String, ?> uriVariables) throws RestClientException {
		URI expanded = getUriTemplateHandler().expand(url, uriVariables);
		return doExecute(expanded, method, requestCallback, responseExtractor);
	}

    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
		ResponseExtractor<T> responseExtractor) throws RestClientException {
		Assert.notNull(url, "'url' must not be null");
		Assert.notNull(method, "'method' must not be null");
		ClientHttpResponse response = null;
		try {
            //调用子类OAuth2RestTemplate.createRequest
            //request见SimpleBufferingClientHttpRequest
			ClientHttpRequest request = createRequest(url, method);
			if (requestCallback != null) {
                //见OAuth2AccessTokenSupport.OAuth2AuthTokenCallback.doWithRequest
				requestCallback.doWithRequest(request);
			}
            //发送request，获取response
            //execute见SimpleBufferingClientHttpRequest.executeInternal
			response = request.execute();
			handleResponse(url, method, response);
			if (responseExtractor != null) {
				return responseExtractor.extractData(response);
			}
			else {
				return null;
			}
		}
		catch (IOException ex) {
			String resource = url.toString();
			String query = url.getRawQuery();
			resource = (query != null ? resource.substring(0, resource.indexOf(query) - 1) : resource);
			throw new ResourceAccessException("I/O error on " + method.name() +
					" request for \"" + resource + "\": " + ex.getMessage(), ex);
		}
		finally {
			if (response != null) {
				response.close();
			}
		}
	}
