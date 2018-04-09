package com.bd.roncoo.eshop.inventory.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    private String status;
    private String message;

    public Response(String status) {
        this.status = status;
    }
}
