package com.sanght.shapechallenge.common.exception;

import com.sanght.shapechallenge.common.constant.ErrorCode;

public class NotFoundException extends Exception {
	private int code = ErrorCode.RESOURCE_NOT_FOUND;
	public NotFoundException(String msg){
		super(msg);
	}
}
