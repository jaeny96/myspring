package com.day.dao;

import java.util.List;

//import org.springframework.stereotype.Repository;

import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;


public interface CustomerDAO {
	/**
	 * 고객이 가입한다
	 * @param 추가할 컬럼 정보
	 * @return 없음
	 * @throws 이미 DB에 존재하는 고객인 경우 발생한다
	 */
	public void insert(Customer c) throws AddException;

	/**
	 * 고객이 자신의 정보를 조회하고 로그인한다
	 * @param id
	 * @return 자신의 정보
	 * @throws 
	 */
	public Customer selectById(String id) throws FindException;

	/**
	 * 고객이 자신의 정보를 수정하고 탈퇴한다.
	 * @param 자신의 모든 정보
	 * @return 없음
	 * @throws
	 */
	public void update(Customer c) throws ModifyException;
	
}
