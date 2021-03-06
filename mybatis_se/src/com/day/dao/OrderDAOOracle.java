package com.day.dao;

import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
//import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.FindException;
//import com.day.sql.MyConnection;

public class OrderDAOOracle implements OrderDAO {
	private SqlSessionFactory sqlSessionFactory;

	public OrderDAOOracle() {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

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
			throw new AddException(e.getMessage());
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
			session.insert("com.day.dto.OrderMapper.insertLine", lines);
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		}

//		String insertLinesSQL = "INSERT INTO order_line(order_no, order_prod_no, order_quantity) "
//				+ "VALUES (ORDER_SEQ.CURRVAL,?,?)";
//		// SQL 송신
//		PreparedStatement pstmt = null;
//		try {
//			pstmt = con.prepareStatement(insertLinesSQL);
//			for (OrderLine line : lines) {
//				pstmt.setString(1, line.getOrder_p().getProd_no());
//				pstmt.setInt(2, line.getOrder_quantity());
//				pstmt.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new AddException("주문 상세정보 추가 실패 : " + e.getMessage());
//		} finally {
//			MyConnection.close(null, pstmt, null);
//		}
	}

	@Override
	public void insert(OrderInfo info) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			insertInfo(session, info);
			insertLines(session, info.getLines());
			session.commit();
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//			con.setAutoCommit(false); // 자동 커밋 해제
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new AddException(e.getMessage());
//		}
//
//		try {
//			insertInfo(con, info);
//			insertLines(con, info.getLines());
//
//			con.commit(); // 커밋
//		} catch (Exception e) {
//			try {
//				con.rollback(); // 롤백
//			} catch (SQLException e1) {
//			}
//			throw new AddException(e.getMessage());
//		} finally {
//			// 에러 떠넘길 때 떠넘기더라도 connection은 닫고 떠넘겨라
//			MyConnection.close(con, null, null);
//		}

	}

	private List<OrderLine> selectLines() {
		return null;
	}

	@Override
	public List<OrderInfo> selectById(String id) throws FindException {
		SqlSession session = null;
		List<OrderInfo> list = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			list = session.selectList("com.day.dto.OrderMapper.selectById", id);
			session.commit();
			return list;
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
