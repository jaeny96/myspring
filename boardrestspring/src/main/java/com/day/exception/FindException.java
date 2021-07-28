package com.day.exception;

/*
 * 검색 실패 시 발생할 예외
 */
public class FindException extends Exception{
	public FindException(String message) {
		//부모의 생성자 호출
		super(message);
	}
}
