package com.bd.ace.trade.common.exception;

public class AceMqException extends Exception {
	private static final long serialVersionUID = 4329969184260614641L;

	public AceMqException() {
		super();
	}

	public AceMqException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AceMqException(String message, Throwable cause) {
		super(message, cause);
	}

	public AceMqException(String message) {
		super(message);
	}

	public AceMqException(Throwable cause) {
		super(cause);
	}
}
