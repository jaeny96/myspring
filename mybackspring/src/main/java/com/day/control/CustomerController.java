package com.day.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.day.dto.Customer;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.service.CustomerService;

@Controller
public class CustomerController {
	private Logger log = Logger.getLogger(ProductController.class.getName());
	@Autowired
	CustomerService service;

	@RequestMapping("/login")
	public String login(String id, String pwd, HttpSession session) {
		String viewPath = "";
		session.removeAttribute("loginInfo");
		try {
			Customer loginInfo = service.login(id, pwd);
//			System.out.println("로그인 인포 "+ loginInfo);
			session.setAttribute("loginInfo", loginInfo);
//			System.out.println("로그인 info "+session.getAttribute("loginInfo"));
			viewPath = "success";
		} catch (Exception e) {
			viewPath = "fail";
		}
		return viewPath;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
//		session.invalidate();// 세션제거
		return service.logout(session);
	}

	@GetMapping("/iddupchk")
	public void idDupChk(String id, Model model) {
		try {
			Customer c = service.findById(id);
//			System.out.println("iddupchk 내 c입니다 "+c);
			model.addAttribute("dupchkresult", "0");
		} catch (FindException e) {
			model.addAttribute("dupchkresult", "1");
		}
	}

	@PostMapping("/signup")
	public void signUp(Customer c, Model model) {
		log.info(c);
		try {
			service.signup(c);
			model.addAttribute("result", "1");
		} catch (AddException e) {
			e.printStackTrace();
			model.addAttribute("result", "0");
		}
	}
}
