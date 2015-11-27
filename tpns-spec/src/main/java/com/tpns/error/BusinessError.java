package com.tpns.error;

public class BusinessError {

	private String message;

	private BusinessErrorCode code;

	private BusinessError(String message, BusinessErrorCode code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public BusinessErrorCode getCode() {
		return code;
	}

	public static BusinessError create(String message) {
		return new BusinessError(message, BusinessErrorCode.GENERIC);
	}

	public static BusinessError create(String message, BusinessErrorCode code) {
		return new BusinessError(message, code);
	}

}
