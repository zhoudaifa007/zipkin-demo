package com.frank.cloud.message.common.exception;

import java.text.MessageFormat;

import com.frank.cloud.message.common.enums.ErrorCode;

public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 2166347216219675858L;
	
	ErrorCode errorCode;
	
	String msg;

	public AppException() 
	{
	}

	public AppException(ErrorCode errorCode, Object... arguments) {
		super(MessageFormat.format(errorCode.getMsg(), arguments));
		this.msg = MessageFormat.format(errorCode.getMsg(), arguments);
		this.errorCode = errorCode;
	}

	public AppException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMsg(), cause);
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public AppException(Throwable cause) 
	{
		super(cause);
	}

	public AppException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
