import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.dto.Product;

public class Test {
	public static void main(String[] args) {
//		String resource = "org/mybatis/example/mybatis-config.xml";
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			// session 객체가 jdbc의 Connection과 같은 역할을 해줌
			SqlSession session = sqlSessionFactory.openSession();
			// ---------------- ProductMapper
//			Product p = session.selectOne("com.day.dto.ProductMapper.selectByNo","C0001");
//			System.out.println(p);
//			
//			List<Product> pList = session.selectList("com.day.dto.ProductMapper.selectAll");
////			System.out.println(pList);
//			System.out.println("====================================================================================================================");
//			for(Product pe : pList) {
//				System.out.println(pe);
//			}
//			HashMap<String,Integer> map = new HashMap<>();
//			map.put("currentPage", 2);
//			map.put("cnt_per_page", 4);
//			List<Product> pListPage = session.selectList("com.day.dto.ProductMapper.selectAllPage",map);
//			System.out.println("====================================================================================================================");
//			for(Product ppe : pListPage) {
//				System.out.println(ppe);
//			}
//			System.out.println("====================================================================================================================");
////			List<Product> pListWord = session.selectList("com.day.dto.ProductMapper.selectByName","%아%");
////			List<Product> pListWord = session.selectList("com.day.dto.ProductMapper.selectByName","아");
//			HashMap<String,String> map1 = new HashMap<>();
//			map1.put("word","리");
//			map1.put("ordermethod","prod_name DESC");
//			List<Product> pListWord = session.selectList("com.day.dto.ProductMapper.selectByName",map1);
//			for(Product pwe : pListWord) {
//				System.out.println(pwe);
//			}
			// ---------------- CustomerMapper
//			System.out.println("고객 추가");
//			HashMap<String,String> cmap = new HashMap<>();
//			cmap.put("id", "id10");
//			cmap.put("pwd", "p10");
//			cmap.put("name", "오지수");
//			cmap.put("buildingno", "3");
//			session.insert("com.day.dto.CustomerMapper.insert", cmap);
//			Customer c = new Customer();
//			c.setId("id13");
//			c.setPwd("pwd13");
//			c.setName("name13");
//			c.setBuildingno("3");
//			session.insert("com.day.dto.CustomerMapper.insert", c);
//			session.commit();
			// myBatis는 autoCommit이 아님

//			System.out.println("고객 정보 수정 - 탈퇴");
//			Customer c1 = new Customer();
//			c1.setId("id11");
//			c1.setEnabled(0);
//			session.update("com.day.dto.CustomerMapper.update",c1);

//			System.out.println("고객 정보 수정 - 정보 변경");
//			Customer c2 = new Customer();
//			c2.setId("id13");
//			c2.setPwd("pp33");
//			c2.setBuildingno("2");
//			c2.setName("name133");
//			c2.setEnabled(1);
//			session.update("com.day.dto.CustomerMapper.update",c2);
//			session.commit();

			// ---------------- CustomerMapper
//			System.out.println("고객 삭제");
//			String id = "id13";
//			int rowcnt = session.delete("com.day.dto.CustomerMapper.delete", id);
//			if (rowcnt == 1) {
//				System.out.println(id + "가 삭제됨");
//				session.commit();
//			} else {
//				System.out.println(id + "가 존재하지 않아 삭제하지 못함");
//			}
			
//			Customer c = session.selectOne("com.day.dto.CustomerMapper.selectById","id1");
//			System.out.println(c);
			
			System.out.println("주문 추가!");
			OrderInfo info = new OrderInfo();
			Customer order_c = new Customer();
			order_c.setId("id1");
			info.setOrder_c(order_c);
			
			List<OrderLine> lines = new ArrayList<>();
			
			OrderLine line = new OrderLine();
			Product order_p= new Product();
			order_p.setProd_no("G0001");
			line.setOrder_p(order_p);
			line.setOrder_quantity(10);
			lines.add(line);

			OrderLine line2 = new OrderLine();
			Product order_p2= new Product();
			order_p2.setProd_no("G0002");
			line2.setOrder_p(order_p2);
			line2.setOrder_quantity(20);
			lines.add(line2);
			
			info.setLines(lines);
			
			session.insert("com.day.dto.OrderMapper.insertInfo",info);
//			for(OrderLine getline : info.getLines()) {
//				System.out.println(getline);
//			}
			for(OrderLine getline : info.getLines()) {
				session.insert("com.day.dto.OrderMapper.insertLine",getline);				
			}
			session.commit();
			
			List<OrderInfo> oiList = session.selectList("com.day.dto.OrderMapper.selectById","id1");
			for(OrderInfo oi : oiList) {
				int order_no= oi.getOrder_no();
				Date order_dt=oi.getOrder_dt();
				System.out.println("------------주문번호 : "+ order_no + " 주문일자 : "+order_dt+"------------");
				List<OrderLine> olList = oi.getLines();
				for(OrderLine ol : olList){
					Product p1 = ol.getOrder_p();
					int quantity=ol.getOrder_quantity();
					System.out.println("상품번호 : "+p1.getProd_no()+" ,상품명 : "+p1.getProd_name()+" ,상품가격 : " +p1.getProd_price()+" ,주문수량 : " + quantity);
				}
				System.out.println("----------------------------------------------------------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
