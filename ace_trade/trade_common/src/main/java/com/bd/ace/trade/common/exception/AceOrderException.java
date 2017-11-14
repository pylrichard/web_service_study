package com.bd.ace.trade.common.exception;

public class AceOrderException extends RuntimeException {
    public AceOrderException() {
        super();
    }

    public AceOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AceOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AceOrderException(String message) {
        super(message);
    }

    public AceOrderException(Throwable cause) {
        super(cause);
    }
}
