package com.day.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.day.dao.ProductDAO;
import com.day.dto.Product;
import com.day.exception.FindException;

/*
 * 사용자와 DAO와 완충작용을 해주는 단
 * dao와 화면단을 연결해주는 service단
 */

@Service
public class ProductService {
	@Autowired
	@Qualifier("productDAO1")
	private ProductDAO dao;
	public ProductService() {
		
	}
//	@Autowired(required=false)
//	@Autowired
//	public ProductService(ProductDAO dao) {
//		this.dao=dao;
//		System.out.println("ProductService의 생성자 호출됨");
//	}
//	public void setDao(ProductDAO dao) {
//		System.out.println("setDao 호출됨");
//		this.dao=dao;
//	}
	public ProductDAO getDao() {
		return dao;
	}

	public List<Product> findAll() throws FindException {
		return dao.selectAll();
		/*
		 * List<Product> list = dao.selectAll(); list를 암호화 암호화된 리스트 반환
		 */
	}

	public List<Product> findAll(int currentPage) throws FindException {
		return dao.selectAll(currentPage);
	}

	public Product findByNo(String prod_no) throws FindException {
		/*
		 * 상품 번호를 복호화 dao.selectByNo(복호화된 값);
		 */
		return dao.selectByNo(prod_no);
	}

	public List<Product> findByName(String word) throws FindException {
		return dao.selectByName(word);
	}

}
