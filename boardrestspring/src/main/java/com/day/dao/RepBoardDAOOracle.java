package com.day.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.day.dto.Customer;
import com.day.dto.RepBoard;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.exception.RemoveException;

@Repository
public class RepBoardDAOOracle implements RepBoardDAO {
	@Autowired
	@Qualifier("UnderscoreToCamelCase")
	private SqlSessionFactory sessionFactory;

	@Override
	public void insert(RepBoard repBoard) throws AddException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
//			System.out.println(repBoard);
			session.insert("com.day.dto.RepBoardMapper.insert", repBoard);
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public RepBoard selectByNo(int boardNo) throws FindException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			return session.selectOne("com.day.dto.RepBoardMapper.selectByNo", boardNo);
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<RepBoard> selectAll() throws FindException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			return session.selectList("com.day.dto.RepBoardMapper.selectAll");
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<RepBoard> selectByWord(String word) throws FindException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			return session.selectList("com.day.dto.RepBoardMapper.selectByWord", word);
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void plustViewCount(int boardNo) throws ModifyException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			session.update("com.day.dto.RepBoardMapper.plusViewCount", boardNo);
		} catch (Exception e) {
			throw new ModifyException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void update(RepBoard repBoard) throws ModifyException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			int rowcnt = session.update("com.day.dto.RepBoardMapper.update", repBoard);
			if (rowcnt == 0) {
				throw new ModifyException("해당 게시글의 작성자가 아니거나 해당 게시글이 없습니다");
			}
		} catch (Exception e) {
			throw new ModifyException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	@Transactional
	public void delete(RepBoard repBoard) throws RemoveException {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			deleteReply(session, repBoard.getBoardNo());
			deleteWrite(session, repBoard);
		} catch (Exception e) {
			throw new RemoveException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private void deleteReply(SqlSession session, int boardNo) throws RemoveException {
		try {
			session.delete("com.day.dto.RepBoardMapper.deleteReply", boardNo);
		} catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}
	}

	private void deleteWrite(SqlSession session, RepBoard repBoard) throws RemoveException {
		try {
			int rowcnt = session.delete("com.day.dto.RepBoardMapper.delete", repBoard);
			if (rowcnt == 0) {
				throw new RemoveException("게시글 번호가 없거나 게시글의 작성자가 아닙니다");
			}
		} catch (Exception e) {
			throw new RemoveException(e.getMessage());
		}
	}

}
