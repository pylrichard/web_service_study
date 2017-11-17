package com.bd.roncoo.book.shop.admin.support;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
    public static void main(String[] args) {
        configureFor("127.0.0.1", 8080);
        stubFor(get(urlEqualTo("/book")).willReturn(okJson("{\"name\":\"pyl\"}")));
    }
}
