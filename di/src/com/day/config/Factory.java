package com.day.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.day.dao.CustomerDAO;
import com.day.dao.CustomerDAOOracle;
import com.day.dao.ProductDAO;
import com.day.dao.ProductDAOOracle;
import com.day.dto.Customer;
import com.day.dto.Product;
import com.day.service.ProductService;

//설정 용도의 클래스 
@Configuration
public class Factory {
	@Bean
	public Customer c() {
		Customer customer = new Customer();
		customer.setId("id1");
		customer.setPwd("p1");;
		customer.setName("오지수");
		return customer;
	}
	@Bean
	public CustomerDAO customerDAO() {
		return new CustomerDAOOracle();
	}
	@Bean
	public Product p() {
		Product product = new Product("C0001","아메리카노",1000);
		return product;
	}
	@Bean
	public ProductDAO productDAO() {
		return new ProductDAOOracle();
	}
	@Bean
	public ProductService productService() {
//		return new ProductService(productDAO());
		return new ProductService();
	}
	
}
