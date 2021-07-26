package com.day.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.day.dao.CustomerDAO;
import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;

@Service
public class CustomerService {
	@Autowired
	private CustomerDAO dao;

	/**
	 * 고객이 회원가입한다
	 * 
	 * @param c 추가할 고객의 정보
	 * @throws AddException 회원가입 실패(아이디 중복)했을 경우 발생
	 */
	public void signup(Customer c) throws AddException {
		dao.insert(c);
	}

	/**
	 * 고객이 로그인한다
	 * 
	 * @param id  고객의 아이디
	 * @param pwd 고객의 비밀번호
	 * @throws FindException DB에서 해당 아이디와 비밀번호에 해당하는 값을 찾지 못했을 경우 발생
	 */
	public Customer login(String id, String pwd) throws FindException {
		Customer c = dao.selectById(id);
		if (!c.getPwd().equals(pwd)) {
			throw new FindException("로그인 실패!");
		}
		return c;
	}
	/**
	 * 고객이 로그아웃한다
	 * 
	 * @param session  로그인 되어있던 고객의 아이디정보를 담고 있는 세션
	 */
	public String logout(HttpSession session){
		session.invalidate();// 세션제거
		return "success";
	}

	/**
	 * 고객이 자신의 정보를 조회한다
	 * 
	 * @param id 고객의 아이디
	 * @return 조회한 고객의 정보
	 * @throws FindException 고객을 찾지 못했을 경우 발생
	 */
	public Customer detail(String id) throws FindException {
		return dao.selectById(id);
	}
	/**
	 * 고객이 자신의 정보를 조회한다
	 * 
	 * @param id 고객의 아이디
	 * @return 조회한 고객의 정보
	 * @throws FindException 고객을 찾지 못했을 경우 발생
	 */
	public Customer findById(String id) throws FindException {
		Customer c = dao.selectById(id);
		if(c!=null) {
			return c;			
		}else {
			throw new FindException("해당 아이디를 찾을 수 없습니다");
		}
	}

	/**
	 * 고객의 정보를 수정한다
	 * 
	 * @param c 고객 정보
	 * @throws ModifyException 고객의 정보를 수정할 수 없을 경우 발생
	 */
	public void modify(Customer c) throws ModifyException {
		if (c.getEnabled() == 0) {
			throw new ModifyException("이미 탈퇴한 회원입니다.");
		}
		c.setEnabled(-1);
		dao.update(c);
	}

	/**
	 * 고객이 탈퇴한다
	 * 
	 * @param c 고객 정보
	 * @throws ModifyException 탈퇴처리 못했을 경우 발생
	 */
	public void leave(Customer c) throws ModifyException {
		c.setEnabled(0);
		dao.update(c);
	}

	public void reEnter(Customer c) throws ModifyException {
		c.setEnabled(1);
		dao.update(c);
	}

}
