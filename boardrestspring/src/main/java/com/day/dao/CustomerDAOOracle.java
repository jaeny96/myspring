package com.day.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Scanner;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.day.dto.Customer;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.DuplicatedException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;

@Repository("customerDAO")
public class CustomerDAOOracle implements CustomerDAO {
	@Autowired
	@Qualifier("Underscore")
//	private DataSource ds;
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insert(Customer c) throws AddException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			session.insert("com.day.dto.CustomerMapper.insert",c);
			session.commit();
		} catch (Exception e) {
			throw new AddException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Customer selectById(String id) throws FindException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			Customer c = session.selectOne("com.day.dto.CustomerMapper.selectById",id);
			if(c==null) {
				throw new FindException("해당 고객을 찾을 수 없습니다");
			}
			//			System.out.println("selectById의 c " + c);
			return c;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		
//		Connection con = null;
//		try {
//			con = MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//
//		String selectSQL = "SELECT * FROM customer WHERE id=?";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Customer c = null;
//		try {
//			pstmt = con.prepareStatement(selectSQL);
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				String pwd = rs.getString("pwd");
//				String name = rs.getString("name");
//				String buildingno = rs.getString("buildingno");
//				int enabled = rs.getInt("enabled");
//
//				c = new Customer(id, pwd, name, buildingno, enabled);
//				return c;
//			} else {
//				throw new FindException("해당 고객을 찾을 수 없습니다.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			MyConnection.close(con, pstmt, rs);
//		}
	}

	@Override
	public void update(Customer c) throws ModifyException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			session.update("com.day.dto.CustomerMapper.update",c);
			session.commit();
		} catch (Exception e) {
			throw new ModifyException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	public static void main(String[] args) {
		// 고객은 가입한다.
//		String id="";
//		String pwd="";
//		String name="";
//		
//		try {
//			CustomerDAOOracle dao = new CustomerDAOOracle();
//			Customer c = new Customer(id,pwd,name);
//			dao.insert(c);
//		}catch (DuplicatedException e) {
//			System.out.println(e.getMessage());
//		}
//		catch (AddException e) {
//			System.out.println(e.getMessage());
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}

		// 고객은 로그인한다.

		// 고객은 자기정보를 조회한다.
//		String id="";
//		try {
//			CustomerDAOOracle dao = new CustomerDAOOracle();
//			Customer c = dao.selectById(id);
//			System.out.println(c);
//		}catch (FindException e) {
//			System.out.println(e.getMessage());
//		}catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		// 고객은 자기정보를 수정한다.
//		Scanner sc = new Scanner(System.in);
//		System.out.println("아이디를 입력하세요");
//		String id = sc.nextLine();
//		System.out.println("변경할 비밀번호를 입력하세요");
//		String pwd = sc.nextLine();
//		System.out.println("변경할 이름을 입력하세요");
//		String name = sc.nextLine();
//		System.out.println("변경할 빌딩 번호를 입력하세요");
//		String buildingno = sc.nextLine();
//		
//		try {
//			CustomerDAOOracle dao = new CustomerDAOOracle();
//			Customer c = new Customer(id,pwd,name,buildingno);
//			dao.update(c);
//		}catch (ModifyException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		// 고객은 탈퇴한다.

	}
}
