package com.day.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.day.dao.RepBoardDAO;
import com.day.dto.Customer;
import com.day.dto.RepBoard;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;

@Service
public class RepBoardService {
	@Autowired
	private RepBoardDAO dao;

	/**
	 * 글쓰기
	 * 
	 * @param repBoard
	 * @throws AddException
	 */
	public void write(RepBoard repBoard) throws AddException {
		repBoard.setParentNo(0);
		dao.insert(repBoard);
	}

	/**
	 * 답글쓰기
	 * 
	 * @param repBoard
	 * @throws AddException
	 */
	public void reply(RepBoard repBoard) throws AddException {
		if (repBoard.getParentNo() == 0) {
			throw new AddException("부모글 번호가 필요합니다");
		}
		dao.insert(repBoard);
	}

	public List<RepBoard> list() throws FindException {
		return dao.selectAll();
	}

	public List<RepBoard> list(String word) throws FindException {
		return dao.selectByWord(word);
	}

	/**
	 * 글 상세보기
	 * 
	 * @param no
	 * @return
	 * @throws FindException
	 * @throws ModifyException
	 */
	public RepBoard detail(int no) throws FindException {
		try {
			dao.plustViewCount(no);
		} catch (ModifyException e) {
			e.printStackTrace();
			throw new FindException("조회수 증가 실패 : " + e.getMessage());
		}
		return dao.selectByNo(no);
	}

	public void modify(int no, RepBoard repBoard) throws ModifyException {
		dao.update(repBoard);
	}

	public void remove(RepBoard repBoard) throws RemoveException {
		dao.delete(repBoard);
	}
}
