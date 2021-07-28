package com.day.exception;

/*
 * 수정 실패 시 발생할 예외
 */
public class ModifyException extends Exception{
	public ModifyException(String message) {
		super(message);
	}
}
