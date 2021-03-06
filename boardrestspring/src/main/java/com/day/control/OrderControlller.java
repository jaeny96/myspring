package com.day.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.service.OrderService;

//@Controller
//@ResponseBody
public class OrderControlller {
	@Autowired
	OrderService service;

	@GetMapping("/addorder")
//	@ResponseBody
	public Map<String, Object> addOrder(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Customer c = (Customer) session.getAttribute("loginInfo");
		if (c == null) {
			map.put("status", 0);
		} else {
			// 1.장바구니내용
			Map<String, Integer> cart = (Map) session.getAttribute("cart");
			if (cart != null && cart.size() > 0) {
				// 2.장바구니내용을 OrderInfo객체로 변환
				OrderInfo info = new OrderInfo();
				List<OrderLine> lines = new ArrayList<>();
				for (String prod_no : cart.keySet()) {
					int quantity = cart.get(prod_no);

					OrderLine line = new OrderLine(); // 주문상세
					Product order_p = new Product();
					order_p.setProd_no(prod_no);
					line.setOrder_p(order_p); // 주문상품
					line.setOrder_quantity(quantity);// 주문수량
					lines.add(line);
				}
				info.setLines(lines); // 주문상세들
				info.setOrder_c(c); // 주문자, 주문일자랑 주문번호는 DB에서 시퀀스로 설계해줌
				try {
					service.add(info);
					session.removeAttribute("cart");// 장바구니 비우기
					map.put("status", 1);
				} catch (AddException e) { // 추가실패인 경우
					e.printStackTrace();
					map.put("msg", e.getMessage());
					map.put("status", -2);
				}
			} else { // 장바구니가 비어있는 경우
				map.put("status", -1);
			}
		}
		return map;
	}
	
	@GetMapping("/orderlist")
//	@ResponseBody
	public Object showOrderlist(HttpSession session){
		Map<String, Object> map = new HashMap<>();
		Customer c= (Customer) session.getAttribute("loginInfo");
		if(c==null) {
			map.put("status", 0);
			return map;
		}else {
			try {
				List<OrderInfo> infos = service.findById(c.getId());
				System.out.println(infos);
				return infos;
			} catch (FindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				map.put("status",-3);
				map.put("msg",e.getMessage());
				return map;
			}
		}
	}
}
