package com.day.exception;

/*
 * 추가 실패 시 발생할 예외
 */
public class AddException extends Exception{
	public AddException(String message) {
		//부모의 생성자 호출
		super(message);
	}
}
