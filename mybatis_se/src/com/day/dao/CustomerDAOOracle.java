package com.day.dao;

import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.springframework.stereotype.Repository;

import com.day.dto.Customer;
//import com.day.dto.Product;
import com.day.exception.AddException;
//import com.day.exception.DuplicatedException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
//import com.day.sql.MyConnection;

//@Repository("customerDAO")
public class CustomerDAOOracle implements CustomerDAO {
	private SqlSessionFactory sqlSessionFactory;

	public CustomerDAOOracle() {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void insert(Customer c) throws AddException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			session.insert("com.day.dto.CustomerMapper.insert", c);
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
			Customer c = session.selectOne("com.day.dto.CustomerMapper.selectById", id);
			session.commit();
			return c;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void update(Customer c) throws ModifyException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			session.update("com.day.dto.CustomerMapper.update", c);
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
