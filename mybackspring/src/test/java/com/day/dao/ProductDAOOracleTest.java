package com.day.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.day.dto.Product;
import com.day.exception.FindException;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

class ProductDAOOracleTest {
	@Autowired
	@Qualifier("productDAO1")
	private ProductDAO dao;
	private Logger log = Logger.getLogger(ProductDAOOracleTest.class.getName());

	@Test
	void testSelectByNo() throws FindException {
//		fail("Not yet implemented");
//		System.out.println("@Test testSelectByNo메서드 호출");
//		log.debug("testSelectByNo메서드 호출");
		log.info("testSelectByNo메서드 호출");
//		log.warn("testSelectByNo메서드 호출");
//		log.error("testSelectByNo메서드 호출");
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
		String word = "아";
		List<Product> list = dao.selectByName(word);

		int expectedSize = 2;
		String expectedProd_no = "C0001";
		assertTrue(list.size() == expectedSize);
		assertEquals(expectedProd_no, list.get(1).getProd_no());
	}

	@Test
	void testSelectByNameNotFound() {
		System.out.println("@Test testSelectByNameNotFound메서드 호출");
		String word = "가";
		FindException expectedException;
		expectedException = assertThrows(FindException.class, () -> dao.selectByName(word));

		String expectedMsg = "상품이 없습니다.";
		assertEquals(expectedMsg, expectedException.getMessage());
	}
}
