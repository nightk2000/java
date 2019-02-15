package com.demo.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopHandleActuator {

	final static Logger log = LoggerFactory.getLogger(AopHandleActuator.class);
	
	ThreadLocal<Long> beginTime = new ThreadLocal<>();
	
    @Pointcut("@annotation(AopHandle)")
    public void pointCut() {
    	
    }
	
    
    //@Before("pointCut()")
    @Before("pointCut() && @annotation(aop) && (args(..,req)||args(req,..)) ")
    public void doBefore(JoinPoint joinPoint,AopHandle aop,HttpServletRequest req)
    {
    	//ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	//HttpServletRequest req = attributes.getRequest();
    	
    	beginTime.set(System.currentTimeMillis());
    	Object[] args = joinPoint.getArgs();	// 被注解AOP的方法的参数
    	for(Object obj:args) {
    		System.out.println(obj);
    	}
    	
    	log.info("before msg:{}    RemoteAddr:{}   data:{}", aop.msg(), req.getRemoteAddr(), req.getParameter("id"));
    }

    @After("pointCut()")
	public void doAfter(JoinPoint joinPoint) {
		log.info("after time:{}, msg:{}", System.currentTimeMillis() - beginTime.get());
	}

    
    /**
     * controller run over, before return to client
     * @param joinPoint
     * @param result
     */
	@AfterReturning(value = "pointCut()", returning = "result") 
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String methedName = joinPoint.getSignature().getName();
		log.info("afterReturning The methed " + methedName + " ends with " + result);
	}
    
	@AfterThrowing(value = "pointCut()", throwing = "ex")
	public void afterThrowin(JoinPoint joinPoint, Exception ex) {
		String methedName = joinPoint.getSignature().getName();
		log.info("afterThrowin The methed " + methedName + " eccurs exception " + ex);
	}

}