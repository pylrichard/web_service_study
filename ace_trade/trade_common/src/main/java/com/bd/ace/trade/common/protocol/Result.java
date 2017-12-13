package com.bd.ace.trade.common.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private String retCode;
    private String retInfo;
    private T data;
}
