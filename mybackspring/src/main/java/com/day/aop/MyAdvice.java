package com.day.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//Filter처럼 aop이용하여 포인트컷 비즈니스 로직 수행 전후에 매개변수를 조작한다거나 수행된 결과값을 조작할 수 있음
@Aspect
public class MyAdvice {
	private Logger log = Logger.getLogger(MyAdvice.class);
	
	@Before("execution(* log*(..)) ||  execution(* detail(**))" ) //log로 시작하는 메서드 만나면 메서드 호출 직전에 beforeLog()메서드가 호출됨
	public void beforeLog() {
		log.error("Before");
	}	
	
	@Around("execution(* log*(..))")
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable{ //포인트컷 메서드 호출 전후에 aroundLog()의 내용이 수행됨
		String pMethodName = pjp.getSignature().getName();
		log.error("Around 포인트 컷 메서드 ("+pMethodName+ ") 호출 전");
		Object obj = pjp.proceed(); //포인트 컷 메서드 호출
		Object[] args = pjp.getArgs();
		for(Object arg : args) {
			log.error("포인트컷 메서드 매개변수 " + arg); // 이런 매개변수들 암호화 등의 작업을 통해 조작할 수 있음
		}
		log.error("Around 포인트 컷 메서드 ("+pMethodName+ ") 호출 후");
		return obj;
	}
}
