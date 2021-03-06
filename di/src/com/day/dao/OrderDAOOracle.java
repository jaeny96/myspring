package com.day.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.sql.MyConnection;

public class OrderDAOOracle implements OrderDAO {
	/**
	 * 주문의 기본정보를 추가한다
	 * 
	 * @param con  DB연결 객체
	 * @param info 주문 기본정보
	 * @throws AddException
	 */
	private void insertInfo(Connection con, OrderInfo info) throws AddException {
		String insertInfoSQL = "INSERT INTO order_info(order_no, order_id) VALUES (ORDER_SEQ.NEXTVAL,?)";
		// SQL 송신
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertInfoSQL);
			pstmt.setString(1, info.getOrder_c().getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("주문 기본정보 추가 실패 : " + e.getMessage());
		} finally {
			MyConnection.close(null, pstmt, null);
		}
	}

	/**
	 * 주문의 상세정보를 추가한다.
	 * @param con DB연결객체
	 * @param lines 주문상세정보
	 * @throws AddException
	 */
	private void insertLines(Connection con, List<OrderLine> lines) throws AddException {
		String insertLinesSQL = "INSERT INTO order_line(order_no, order_prod_no, order_quantity) "
				+ "VALUES (ORDER_SEQ.CURRVAL,?,?)";
		// SQL 송신
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertLinesSQL);
			for (OrderLine line : lines) {
				pstmt.setString(1, line.getOrder_p().getProd_no());
				pstmt.setInt(2, line.getOrder_quantity());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("주문 상세정보 추가 실패 : " + e.getMessage());
		} finally {
			MyConnection.close(null, pstmt, null);
		}
	}

	@Override
	public void insert(OrderInfo info) throws AddException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
			con.setAutoCommit(false); //자동 커밋 해제
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		
		try {
			insertInfo(con, info);
			insertLines(con, info.getLines());
			
			con.commit(); //커밋
		}catch(Exception e) {
			try {
				con.rollback(); //롤백
			} catch (SQLException e1) {
			}
			throw new AddException(e.getMessage());
		}
		finally {
			//에러 떠넘길 때 떠넘기더라도 connection은 닫고 떠넘겨라
			MyConnection.close(con, null, null);
		}
		
	}
	
	private List<OrderLine> selectLines(){
		return null;
	}
	
	@Override
	public List<OrderInfo> selectById(String id) throws FindException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}	
		
		String selectByIdSQL="SELECT oi.order_no,order_dt, order_prod_no, prod_name, order_quantity\r\n"
				+ "FROM order_info oi JOIN order_line ol ON (oi.order_no=ol.order_no)\r\n"
				+ "JOIN product p ON(ol.order_prod_no=p.prod_no)\r\n"
				+ "WHERE order_id=?\r\n"
				+ "ORDER BY oi.order_no DESC,order_prod_no";
		
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		//과제
		try {
			pstmt=con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				OrderInfo info = new OrderInfo();
				info.setOrder_no(rs.getInt("order_no"));
				info.setOrder_dt(rs.getDate("order_dt"));
				
				OrderLine line = new OrderLine();
				Product p = new Product();
				p.setProd_no(rs.getString("order_prod_no"));
				line.setOrder_p(p);
				line.setOrder_quantity(rs.getInt("order_quantity"));
				
				List<OrderLine> lineArr = new ArrayList<OrderLine>();
				lineArr.add(line);
				
				info.setLines(lineArr);
				
				list.add(info);
			}
			if(list.size()==0) {
				throw new FindException("주문 내역이 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	public static void main(String[] args) {
		//주문 기본정보와 주문 상세정보 확인 테스트
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
		
		//주문내역 확인 테스트
		String id = "id3";
		OrderDAOOracle dao = new OrderDAOOracle();
		List<OrderInfo> orderInfo;
		try {
			orderInfo = dao.selectById(id);
			for(OrderInfo info : orderInfo) {
				System.out.print(info.getOrder_no()+"/"+info.getOrder_dt());
				for(OrderLine line : info.getLines()) {
					System.out.print("/"+line.getOrder_p().getProd_no()+"/"+line.getOrder_quantity());
				}
				System.out.println();
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
