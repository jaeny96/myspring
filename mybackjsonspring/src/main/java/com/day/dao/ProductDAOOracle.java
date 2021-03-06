package com.day.dao;

//import java.io.IOException;
import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.day.dto.Product;
import com.day.exception.FindException;
//import com.day.sql.MyConnection;

@Repository("productDAO1")
public class ProductDAOOracle implements ProductDAO {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

//	public ProductDAOOracle() {
//		String resource = "mybatis-config.xml";
//		InputStream inputStream;
//		try {
//			inputStream = Resources.getResourceAsStream(resource);
//			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

	@Override
	public List<Product> selectAll() throws FindException {
		SqlSession session = null;
		try {
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			session = sqlSessionFactory.openSession();
			List<Product> list = session.selectList("com.day.dto.ProductMapper.selectAll");
			return list;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Product> selectAll(int currentPage) throws FindException {
		int cnt_per_page = 4;
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			HashMap<String, Integer> map = new HashMap<>();
			map.put("cnt_per_page", cnt_per_page);
			map.put("currentPage", currentPage);
			List<Product> list = session.selectList("com.day.dto.ProductMapper.selectAllPage", map);
			if (list.size() == 0) {
				throw new FindException("상품이 없습니다.");
			}
			return list;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Product selectByNo(String prod_no) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Product p = session.selectOne("com.day.dto.ProductMapper.selectByNo", prod_no);
			if(p==null) {
				throw new FindException("상품이 없습니다");
			}
			return p;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Product> selectByName(String word) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			HashMap<String, String> map = new HashMap<>();
			map.put("word", word);
			map.put("ordermethod", "prod_name");
			List<Product> list = session.selectList("com.day.dto.ProductMapper.selectByName", map);
			if(list.size()==0) {
				throw new FindException("상품이 없습니다.");				
			}
			return list;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

//	public static void main(String[] args) {
		// 상품 전체 조회
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			// 전체 상품 조회
//			List<Product> all = dao.selectAll();
//			for (Product p : all) {
//				System.out.println(p); // p.toString() 메서드 자동호출
//			}
//		}

		// 페이지별 상품 조회
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

		// 상품번호로 상품 내역 조회
//		String prod_no="F0002";
//		System.out.println(prod_no+"에 대한 정보 출력");
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			//상품번호로 상품 내역 조회
//			Product p = dao.selectByNo(prod_no);
//			System.out.println(p);			
//		}

		// 상품이름으로 상품 내역 조회
//		String word = "카";
//		System.out.println("이름에 '" + word + "'가 포함된 상품 정보 출력");
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			List<Product> findName = dao.selectByName(word);
//			for (Product p : findName) {
//				System.out.println(p);
//			}
//		}
//		/*
//		 * 다중 catch문에서 자식 exception 먼저 catch 부모 exception 나중에 catch
//		 */
//		catch (FindException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

}
