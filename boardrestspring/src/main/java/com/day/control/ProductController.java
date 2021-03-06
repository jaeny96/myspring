package com.day.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.day.dto.Product;
import com.day.exception.FindException;
import com.day.service.ProductService;

//@Controller
//@ResponseBody
public class ProductController {
	private Logger log = Logger.getLogger(ProductController.class.getName());

	@Autowired
	private ProductService service;

	@GetMapping("/productinfo")
//	@ResponseBody // 메서드 혹은 클래스 선언 윗부분, 응답 바디에 직접 쓰기 하여면 해당 어노테이션이 필요하다
//	public String productInfo(String prod_no) {
//		String jsonStr;
//		try {
//			Product p = service.findByNo(prod_no);
//			jsonStr="{\"msg\":\"sucess\"}";
//		} catch (FindException e) {
//			e.printStackTrace();
//			jsonStr="실패fail";
//		}
//		return jsonStr;
//	}

//	public Product productInfo(String prod_no) {
//		try {
//			Product p = service.findByNo(prod_no);
//			return p; //Product 타입 객체의 내용을 json객체타입의 문자열로 변환 후 응답 바디에 쓰기가 된다  ~ jackson databind 라이브러리 필요
//		}catch(FindException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public Object productInfo(String prod_no) {
		Map<String, Object> map = new HashMap<>();
		try {
			Product p = service.findByNo(prod_no);
			return p;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map; //map 객체의 내용을 json 객체타입의 문자열로 변환 후 응답 바디에 쓰기가 된다
	}

	@GetMapping("/productlist")
//	@ResponseBody
	public List<Product> productList() {
		List<Product> pList;
		try {
			pList = service.findAll();
			return pList;
		} catch (FindException e) {
			e.printStackTrace();
			return null;
		}
	}
}
