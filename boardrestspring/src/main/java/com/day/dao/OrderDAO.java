package com.day.dao;

import java.util.List;

import com.day.dto.OrderInfo;
import com.day.exception.AddException;
import com.day.exception.FindException;

public interface OrderDAO {
	/**
	 * 주문을 추가한다. (주문 트랜잭션) 주문 기본 정보와 주문 상세정보들을 추가한다.
	 * @param info 
	 * @throws AddException
	 */
	void insert(OrderInfo info) throws AddException;
	
	/**
	 * 주문정보를 검색한다
	 * @param id
	 * @return 검색한 주문 내역들을 반환한다
	 * @throws FindException
	 */
	List<OrderInfo> selectById(String id) throws FindException;
	
}
