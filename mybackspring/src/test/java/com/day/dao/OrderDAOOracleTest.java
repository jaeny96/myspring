package com.day.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.day.control.ProductController;
import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.dto.Product;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

public class OrderDAOOracleTest {
	private Logger log = Logger.getLogger(ProductController.class.getName());

	@Autowired
	private OrderDAOOracle dao;

	@Test
	void testSelectById() throws Exception {
		String id = "id1";
		List<OrderInfo> list = dao.selectById(id);
		int expectedSize = 13;
		log.info(list.size());
		assertTrue(expectedSize == list.size());
	}

	@Test
	void testInsert() throws Exception {
		OrderInfo info = new OrderInfo();
		Customer order_c = new Customer();
		order_c.setId("id12345");
		info.setOrder_c(order_c);
		List<OrderLine> lines = new ArrayList<>();
		lines.add(new OrderLine(new Product("C0001", null, 0), 1));
		lines.add(new OrderLine(new Product("C0002", null, 0), 2));
		lines.add(new OrderLine(new Product("C0003", null, 0), 3));
		info.setLines(lines);
		dao.insert(info);
	}
}
