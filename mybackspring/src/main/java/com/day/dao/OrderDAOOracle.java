package com.day.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.sql.MyConnection;

@Repository
public class OrderDAOOracle implements OrderDAO {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

//	private DataSource ds;
	/**
	 * 주문의 기본정보를 추가한다
	 * 
	 * @param con  DB연결 객체
	 * @param info 주문 기본정보
	 * @throws AddException
	 */
	private void insertInfo(SqlSession session, OrderInfo info) throws AddException {
		try {
			session.insert("com.day.dto.OrderMapper.insertInfo", info);
		} catch (Exception e) {
			throw new AddException("주문 기본정보 추가 실패!");
		}
	}

	/**
	 * 주문의 상세정보를 추가한다.
	 * 
	 * @param con   DB연결객체
	 * @param lines 주문상세정보
	 * @throws AddException
	 */
	private void insertLines(SqlSession session, List<OrderLine> lines) throws AddException {
		try {
			for(OrderLine line : lines) {
				session.insert("com.day.dto.OrderMapper.insertLine", line);				
			}
		} catch (Exception e) {
			throw new AddException("주문 상세정보 추가 실패!");
		}
	}

	@Override
	@Transactional(rollbackFor = AddException.class)
	public void insert(OrderInfo info) throws AddException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			insertInfo(session, info);
			insertLines(session, info.getLines());
//			session.commit();
		} catch (Exception e) {
			session.rollback(); // 롤백
			throw new AddException(e.getMessage());
		} finally {
			if (session != null) {
//				session.close();
			}
		}

	}

	private List<OrderLine> selectLines() {
		return null;
	}

	@Override
	public List<OrderInfo> selectById(String id) throws FindException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			List<OrderInfo> infoList = session.selectList("com.day.dto.OrderMapper.selectById", id);
			if(infoList.size()==0) {
				throw new FindException("주문 내역이 없습니다");
			}
			return infoList;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void main(String[] args) {
		// 주문 기본정보와 주문 상세정보 확인 테스트
//		OrderDAOOracle dao = new OrderDAOOracle();
//		
//		OrderInfo info= new OrderInfo();	
//		Customer c = new Customer();
//		c.setId("id1");
//		info.setOrder_c(c);
//		
//		List<OrderLine> lines = new ArrayList<OrderLine>();
//		for(int i=1; i<=3; i++) {
//			OrderLine line = new OrderLine();
//			Product p= new Product();
//			p.setProd_no("C000"+i);
//			line.setOrder_p(p);
//			line.setOrder_quantity(i);
//			lines.add(line);			
//		}
//		
//		info.setLines(lines);
//		
//		try {
//			dao.insert(info);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}

		// 주문내역 확인 테스트
		String id = "id3";
		OrderDAOOracle dao = new OrderDAOOracle();
		List<OrderInfo> orderInfo;
		try {
			orderInfo = dao.selectById(id);
			for (OrderInfo info : orderInfo) {
				System.out.print(info.getOrder_no() + "/" + info.getOrder_dt());
				for (OrderLine line : info.getLines()) {
					System.out.print("/" + line.getOrder_p().getProd_no() + "/" + line.getOrder_quantity());
				}
				System.out.println();
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
