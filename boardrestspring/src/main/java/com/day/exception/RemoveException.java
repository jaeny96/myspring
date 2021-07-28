package com.day.exception;

/*
 * 삭제 실패 시 발생할 예외
 */
public class RemoveException extends Exception{
	public RemoveException(String message) {
		super(message);
	}
}
