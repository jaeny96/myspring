package com.day.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.day.dto.Product;
import com.day.exception.FindException;

class ProductDAOOracleTest {
	static private ProductDAO dao;

	// 한번 호출
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("@BeforeAll메서드 호출");
		dao = new ProductDAOOracle();
	}

	// 테스트용 메서드들이 각각 호출이 될때 마다
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("@BeforeEach메서드 호출");
	}

	@Test
	void testSelectByNo() throws FindException {
//		fail("Not yet implemented");
		System.out.println("@Test testSelectByNo메서드 호출");
		String prod_no = "C0001";
		Product p = dao.selectByNo(prod_no); // DB 검색결과

		String expectedProdName = "아메리카노"; // 예상값
		int expectedProdPrice = 1000;

		assertEquals(expectedProdName, p.getProd_name());
		assertTrue(expectedProdPrice == p.getProd_price());
	}

	@Test
	void testSelectAll() throws FindException {
//		fail("Not yet implemented");		
		System.out.println("@Test testSelectAll메서드 호출");
		List<Product> list = dao.selectAll(); // DB 검색결과
		int expectedSize = 7;

		assertTrue(list.size() == expectedSize);
	}

	@Test
	void testSelectByName() throws FindException {
//		fail("Not yet implemented");
		System.out.println("@Test testSelectByName메서드 호출");
		String word="아";
		List<Product> list = dao.selectByName(word);
		
		int expectedSize = 2;
		String expectedProd_no="C0001";
		assertTrue(list.size()==expectedSize);
		assertEquals(expectedProd_no, list.get(1).getProd_no());
	}
	
	@Test
	void testSelectByNameNotFound() {
		System.out.println("@Test testSelectByNameNotFound메서드 호출");
		String word = "가";
		FindException expectedException;
		expectedException = assertThrows(FindException.class,
				                         ()->dao.selectByName(word));
	    
		String expectedMsg = "상품이 없습니다.";
		assertEquals(expectedMsg, expectedException.getMessage());
	}


	// 테스트용 메서드들이 끝날때 마다
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("@AfterEach메서드 호출");
	}

	// 테스트용 메서드 완전히 끝나면 호출
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("@AfterAll메서드 호출");
	}
}
