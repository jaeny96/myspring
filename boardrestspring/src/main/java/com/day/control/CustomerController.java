package com.day.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.service.CustomerService;

@Controller
@ResponseBody
public class CustomerController {
	@Autowired
	CustomerService service;
	
	@GetMapping("/checklogined")
//	@ResponseBody
	public Map<String, Object> checkLogined(HttpSession session){
		Map<String, Object> map = new HashMap<>();
		Customer c = (Customer)session.getAttribute("loginInfo");
		int status;
//		System.out.println("chklogined"+c);
		if(c == null) {
			status = 0;
		}else {
			status = 1;
		}
		map.put("status", status);
		return map;
	}
	
	@RequestMapping("/login")
//	@ResponseBody
	public Map<String, Object> login(String id, String pwd, HttpSession session) {
		session.removeAttribute("loginInfo");
		Map<String, Object> map = new HashMap<>();
		Customer loginInfo;
		try {
			loginInfo = service.login(id, pwd);
//			System.out.println(loginInfo);
			session.setAttribute("loginInfo", loginInfo);
			map.put("status",1);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status",-1);
			map.put("msg",e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/logout")
//	@ResponseBody
	public ResponseEntity<String> logout(HttpSession session) {
		service.logout(session);
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.OK);
		return entity;
	}
	
	@GetMapping("/iddupchk")
//	@ResponseBody
	public Map<String,String> idDupChk(String id) {
		Map<String,String> map = new HashMap<>();
		try {
			Customer c =service.findById(id);
			map.put("result","0");
		} catch (FindException e) {
			e.printStackTrace();
			map.put("result","1");
		}
		return map;
	}
	
	@PostMapping("/signup")
//	@ResponseBody
	public Map<String, String> signUp(Customer c) {
		Map<String, String> map = new HashMap<>();
		try {
			service.signup(c);
			map.put("result","1");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("result","0");
		}
		return map;
	}
}
