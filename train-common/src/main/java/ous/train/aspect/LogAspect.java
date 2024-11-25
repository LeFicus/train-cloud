package ous.train.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.ValueFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
public class LogAspect {
	public static Logger LOG = LoggerFactory.getLogger(LogAspect.class);
	public LogAspect(){
		System.out.println("Common LogAspect");
	}
	@Pointcut("execution(public * ous.train..*Controller..*.*(..))")
	public void pointcut(){}

	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint){
		MDC.put("LOG_ID",System.currentTimeMillis()+ RandomUtil.randomString(6));
		// 开始打印请求日志
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Signature signature = joinPoint.getSignature();
		String name = signature.getName();

		// 打印请求信息
		LOG.info("------------- 开始 -------------");
		LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
		LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
		LOG.info("远程地址: {}", request.getRemoteAddr());

		// 打印请求参数
		Object[] args = joinPoint.getArgs();
		// LOG.info("请求参数: {}", JSONObject.toJSONString(args));

		// 排除特殊类型的参数，如文件类型
		Object[] arguments = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof ServletRequest
					|| args[i] instanceof ServletResponse
					|| args[i] instanceof MultipartFile) {
				continue;
			}
			arguments[i] = args[i];
		}
		// 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
		String[] excludeProperties = {};
//        PropertyPreFilters filters = new PropertyPreFilter();
//        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
//        excludefilter.addExcludes(excludeProperties);
//        LOG.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter));

		ValueFilter filter = (o, k, v)->{
			for (String e : excludeProperties){
				if (k.equals(e)){
					return null;
				}
			}
			return v;
		};



		LOG.info("请求参数: {}", JSON.toJSONString(arguments, filter));
	}



	@Around("pointcut()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		// 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
		String[] excludeProperties = {};
//        PropertyPreFilters filters = new PropertyPreFilters();
//        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
//        excludefilter.addExcludes(excludeProperties);
//        LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));

		ValueFilter filter = (o,k,v)->{
			for (String e : excludeProperties){
				if (k.equals(e)){
					return null;
				}
			}
			return v;
		};



		LOG.info("返回参数: {}", JSON.toJSONString(result, filter));
		LOG.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
		return result;
	}

}

