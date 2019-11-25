package com.sanght.shapechallenge.common.exception;

import com.sanght.shapechallenge.common.constant.ErrorCode;

public class PermissionDeniedException extends Exception {
	private int code = ErrorCode.PERMISSION_DENIED;
	public PermissionDeniedException(String msg){
		super(msg);
	}
}