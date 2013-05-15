package xiong.util;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 日志处理 切面类 demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-2
 */
@Component
@Aspect
public class LoggerAspect {
	/*
	 * 操作日志
	 */
	@Around("execution(public * xiong.service..*.*(..))")
	public Object loggerOpt(ProceedingJoinPoint pjp) throws Throwable {
		// 获取要执行的方法名和目标对象类型
		String methodName = pjp.getSignature().getName();
		String clazzName = pjp.getTarget().getClass().getSimpleName();
		System.out.println("=========  操作日志记录  =========");
		// String username = (String) session.getAttribute("username");
		// 后台打印，如果需要写入数据库，编写相应代码就可以完成操作了
		System.out.println("Time:" + new Date().toString());
		System.out.println("Class:" + clazzName);
		System.out.println("Method:" + methodName);

		// 调用目标对象的方法
		Object retVal = pjp.proceed();

		return retVal;
	}
}
