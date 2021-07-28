package com.day.exception;

/*
 * PK 중복 실패 시 발생할 예외
 */
public class DuplicatedException extends AddException{
	public DuplicatedException(String message) {
		super(message);
	}
}
