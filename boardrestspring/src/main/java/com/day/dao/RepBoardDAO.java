package com.day.dao;

import java.util.List;

import com.day.dto.RepBoard;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;

public interface RepBoardDAO {
	/**
	 * 게시글 추가
	 * @param repBoard 추가할 글의 내용
	 * @throws AddException
	 */
	void insert(RepBoard repBoard) throws AddException;
//	/**
//	 * 게시글 추가 시의 현재 boardNo의 값
//	 * @return
//	 * @throws FindException
//	 */
////	int selectBoardNo() throws FindException;
	/**
	 * 글번호로 게시글 검색
	 * @param boardNo
	 * @return 해당 글번호의 글 정보
	 * @throws FindException
	 */
	RepBoard selectByNo(int boardNo) throws FindException;
	/**
	 * 게시글 전체 검색
	 * @return 검색한 전체 게시글 목록
	 * @throws FindException
	 */
	List<RepBoard> selectAll() throws FindException;
	/**
	 * 제목이나 글내용에 단어를 포함한 게시글 검색
	 * @param word
	 * @return 해당 단어를 포함하고 있는 게시글 목록
	 * @throws FindException
	 */
	List<RepBoard> selectByWord(String word) throws FindException;
	/**
	 * 조회수 증가
	 * @param boardNo
	 * @throws ModifyException
	 */
	void plustViewCount(int boardNo) throws ModifyException;
	/**
	 * 게시글 수정 , 글번호는 수정 안되며 글 내용한정
	 * @param repBoard 수정할 글의 정보
	 * @throws ModifyException
	 */
	void update(RepBoard repBoard) throws ModifyException;
	/**
	 * 게시글 삭제
	 * @param repBoard
	 * @throws RemoveException
	 */
	void delete(RepBoard repBoard) throws RemoveException;
}
