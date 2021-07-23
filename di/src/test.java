import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.day.dao.CustomerDAO;
import com.day.dto.Customer;
import com.day.dto.Product;
import com.day.exception.FindException;
import com.day.service.ProductService;

public class test {

	public static void main(String[] args) {
		// 1.스프링 컨테이너(엔진)을 시작시킨다
		// 2.스프링 컨테이너가 설정파일(config.xml)의 bean태그에 설정한 클래스타입의 객체를 생성한다
		ApplicationContext ctx;
		String configLocation = "config.xml";
		ctx = new ClassPathXmlApplicationContext(configLocation);
		
//		ctx = new AnnotationConfigApplicationContext(com.day.config.Factory.class);

		Customer c1 = ctx.getBean("c", com.day.dto.Customer.class);
		System.out.println(c1); // c1.toString()자동 호출

		Customer c2 = ctx.getBean("c", com.day.dto.Customer.class);
		System.out.println(c2); // c2.toString()자동 호출

		//if 결과값이 true이면 스프링 컨테이너에서 c라는 이름의 객체는 한개만 관리가 된다는 의미 : singletonePattern
		System.out.println("c1==c2 : " + (c1==c2));

		CustomerDAO cDAO = ctx.getBean("customerDAO", com.day.dao.CustomerDAO.class);
		System.out.println(cDAO);
		
		try {
			Customer c = cDAO.selectById("id9");
			System.out.println(c);
		}catch(FindException e) {
			e.printStackTrace();
		}
		
		Product p1 = ctx.getBean("p",com.day.dto.Product.class);
		System.out.println(p1);
		
		ProductService productService = ctx.getBean("productService", com.day.service.ProductService.class);
		try {
			System.out.println(productService.findByNo("G0001"));
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
