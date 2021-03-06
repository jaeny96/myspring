package com.day.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.day.dto.Product;
import com.day.exception.FindException;
import com.day.sql.MyConnection;

@Repository("productDAO1")
public class ProductDAOOracle implements ProductDAO {

	@Override
	public List<Product> selectAll() throws FindException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
//			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
			/*
			 * 예외 발생~ 사용자에게 떠넘김 (사용자 : 서비스 레이어)
			 */
			throw new FindException(e.getMessage());
		}

		String selectAllSQL = "SELECT * FROM product ORDER BY prod_no";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				/*
				 * 커서를 이동한 위치에 행이 존재하면 true 반환 커서가 있는 위치에 있는 행의 컬럼값 얻기
				 */
				String prod_no = rs.getString("prod_no");
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				java.sql.Date prod_mf_dt = rs.getDate("prod_mf_dt");

				Product p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);

				list.add(p);
			}
			if (list.size() == 0) {
				throw new FindException("상품이 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			/*
			 * 원시예외 콘솔에 예외 내용 추적해서 출력해줌 - 예외이름/내용/발생라인번호
			 */
			e.printStackTrace();
			/**
			 * SQLException인것은 알지만 SQL의 종류가 많기 때문에 정확히 어떤 상황에서 발생한 예외인지 모호함 따라서 검색하다가 발생된
			 * 예외이다 라는 것을 정확하게 알려주기 위해 예외를 가공 원래 발생된 예외를 가공하는 절차라고 생각하면 됨!
			 */

			throw new FindException(e.getMessage());
		} finally {
			// DB연결 해제 ~ 안할경우 메모리 누수발생
			MyConnection.close(con, pstmt, rs);
		}

	}

	@Override
	public List<Product> selectAll(int currentPage) throws FindException {
		/*
		 * 페이지별 보여줄 목록 수 전체건수 : 7건, 총페이지수 : 2페이지
		 */
		int cnt_per_page = 4;
		Connection con = null;
		try {
			con = MyConnection.getConnection();
//			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
			/*
			 * 예외 발생~ 사용자에게 떠넘김 (사용자 : 서비스 레이어)
			 */
			throw new FindException(e.getMessage());
		}

		String selectAllPageSQL = "SELECT *\r\n" + "FROM (SELECT rownum rn, a.* \r\n" + "    FROM ( SELECT *\r\n"
				+ "            FROM product\r\n" + "            ORDER BY prod_no\r\n" + "        ) a\r\n" + "    )\r\n"
				+ "WHERE rn BETWEEN start_row(?,?) AND end_row(?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();

		try {
			pstmt = con.prepareStatement(selectAllPageSQL);
			pstmt.setInt(1, currentPage);
			pstmt.setInt(2, cnt_per_page);
			pstmt.setInt(3, currentPage);
			pstmt.setInt(4, cnt_per_page);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String prod_no = rs.getString("prod_no");
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				java.sql.Date prod_mf_dt = rs.getDate("prod_mf_dt");

				Product p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);

				list.add(p);
			}
			if (list.size() == 0) {
				throw new FindException("상품이 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public Product selectByNo(String prod_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
//			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
			/*
			 * 예외 발생~ 사용자에게 떠넘김 (사용자 : 서비스 레이어)
			 */
			throw new FindException(e.getMessage());
		}
		String selectByNoSQL = "SELECT * FROM product WHERE prod_no=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product p = null;
		try {
			pstmt = con.prepareStatement(selectByNoSQL);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				java.sql.Date prod_mf_dt = rs.getDate("prod_mf_dt");

				p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);
				
				return p;
			} else {
				throw new FindException("해당 상품을 찾을 수 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public List<Product> selectByName(String word) throws FindException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
//			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		//preparedstatement 사용 시 ?에 대한 값 setString() 해줄때 '' 사용하면 안됨
		String selectByNameSQL = "SELECT * FROM product WHERE prod_name LIKE ? ORDER BY prod_no";
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();
		/*
		 * SQL 구문 송신
		 * 수신결과를 List화 , 반환
		 */
		try {
			pstmt = con.prepareStatement(selectByNameSQL);
			pstmt.setString(1, "%"+word+"%");
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				String prod_no=rs.getString("prod_no");
				String prod_name=rs.getString("prod_name");
				int prod_price=rs.getInt("prod_price");
				Date prod_mf_dt=rs.getDate("prod_mf_dt");
				
				Product p = new Product(prod_no,prod_name,prod_price,prod_mf_dt,null);
				
				list.add(p);
			}
			if(list.size()==0) {
				throw new FindException("상품이 없습니다.");
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
		//상품 전체 조회
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			//전체 상품 조회
//			List<Product> all = dao.selectAll();
//			for(Product p : all) {
//				System.out.println(p); //p.toString() 메서드 자동호출
//			}			
//		}

		
		//페이지별 상품 조회
//		int currentPage = 2;
//		System.out.println(currentPage + "페이지의 내용입니다.");
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			//페이지별 상품 조회
//			List<Product> allPage = dao.selectAll(currentPage);
//			for (Product p : allPage) {
//				System.out.println(p);
//			}			
//		}
		

		//상품번호로 상품 내역 조회		
//		String prod_no="F0002";
//		System.out.println(prod_no+"에 대한 정보 출력");
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			//상품번호로 상품 내역 조회
//			Product p = dao.selectByNo(prod_no);
//			System.out.println(p);			
//		}
		
		//상품이름으로 상품 내역 조회
		String word="카";
		System.out.println("이름에 '"+word+"'가 포함된 상품 정보 출력");
		try {
			ProductDAOOracle dao = new ProductDAOOracle();
			List<Product> findName = dao.selectByName(word);
			for(Product p : findName) {
				System.out.println(p);
			}
		}
		/*
		 * 다중 catch문에서 자식 exception 먼저 catch 부모 exception 나중에 catch
		 */
		catch (FindException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
