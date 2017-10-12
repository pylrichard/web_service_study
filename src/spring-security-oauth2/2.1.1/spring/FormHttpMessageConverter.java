package org.springframework.http.converter;

public class FormHttpMessageConverter implements HttpMessageConverter<MultiValueMap<String, ?>> {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();

	private List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();

	private Charset charset = DEFAULT_CHARSET;

	private Charset multipartCharset;

	@Override
	@SuppressWarnings("unchecked")
	public void write(MultiValueMap<String, ?> map, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if (!isMultipart(map, contentType)) {
			writeForm((MultiValueMap<String, String>) map, contentType, outputMessage);
		}
		else {
			writeMultipart((MultiValueMap<String, Object>) map, outputMessage);
		}
	}

	private void writeForm(MultiValueMap<String, String> form, MediaType contentType,
			HttpOutputMessage outputMessage) throws IOException {
		Charset charset;
		if (contentType != null) {
			outputMessage.getHeaders().setContentType(contentType);
			charset = (contentType.getCharset() != null ? contentType.getCharset() : this.charset);
		}
		else {
			outputMessage.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			charset = this.charset;
		}
		StringBuilder builder = new StringBuilder();
		for (Iterator<String> nameIterator = form.keySet().iterator(); nameIterator.hasNext();) {
			String name = nameIterator.next();
			for (Iterator<String> valueIterator = form.get(name).iterator(); valueIterator.hasNext();) {
				String value = valueIterator.next();
				builder.append(URLEncoder.encode(name, charset.name()));
				if (value != null) {
					builder.append('=');
					builder.append(URLEncoder.encode(value, charset.name()));
					if (valueIterator.hasNext()) {
						builder.append('&');
					}
				}
			}
			if (nameIterator.hasNext()) {
				builder.append('&');
			}
		}
        //此处设置断点，可以看到builder.toString()中包含grant_type=authorization_code&code=授权码&redirect_uri=http://localhost:8080/login
		final byte[] bytes = builder.toString().getBytes(charset.name());
		outputMessage.getHeaders().setContentLength(bytes.length);

		if (outputMessage instanceof StreamingHttpOutputMessage) {
			StreamingHttpOutputMessage streamingOutputMessage = (StreamingHttpOutputMessage) outputMessage;
			streamingOutputMessage.setBody(new StreamingHttpOutputMessage.Body() {
				@Override
				public void writeTo(OutputStream outputStream) throws IOException {
					StreamUtils.copy(bytes, outputStream);
				}
			});
		}
		else {
			StreamUtils.copy(bytes, outputMessage.getBody());
		}
	}
}
