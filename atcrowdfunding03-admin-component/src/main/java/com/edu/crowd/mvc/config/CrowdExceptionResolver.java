package com.edu.crowd.mvc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.exception.LoginAcctAlreadyInUseException;
import com.edu.croed.exception.LoginAcctAlreadyInUseForUpdateException;
import com.edu.croed.exception.LoginFailedException;
import com.edu.croed.util.CrowdUtil;
import com.edu.croed.util.ResultEntity;
import com.google.gson.Gson;

/**
 * @author wyg_edu
 * @date 2020年5月7日 下午9:44:42
 * @version v1.0
 * @ControllerAdvice 表示当前类是一个基于注解的异常处理器类
 * @ExceptionHandler 将一个具体的异常类型和一个方法进行关联
 */
@ControllerAdvice
public class CrowdExceptionResolver {
	
	/**
	 * 数据库插入唯一约束异常(修改用户名)
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
	public ModelAndView loginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// 实际捕获的异常与请求的对象
		return commonResolve("system-error", exception, request, response);
	}
	
	/**
	 * 数据库插入唯一约束异常(用户名)
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
	public ModelAndView loginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// 实际捕获的异常与请求的对象
		return commonResolve("admin-add", exception, request, response);
	}
	
	/**
	 * 登录异常
	 * 
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = LoginFailedException.class)
	public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// 实际捕获的异常与请求的对象
		return commonResolve("admin-login", exception, request, response);
	}

	/**
	 * 空指针异常的处理逻辑
	 * 
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = NullPointerException.class)
	public ModelAndView resolveNullPointException(NullPointerException exception, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// 实际捕获的异常与请求的对象
		return commonResolve("system-error", exception, request, response);
	}

	/**
	 * 数学计算的异常出处理方案
	 * 
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = ArithmeticException.class)
	public ModelAndView resolveMathException(ArithmeticException exception, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// 实际捕获的异常与请求的对象

		return commonResolve("system-error", exception, request, response);
	}

	/**
	 * 异常处理逻辑工具
	 * 
	 * @param viewName
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request,
			HttpServletResponse response) throws IOException {// 实际捕获的异常与请求的对象

		// 1、判断请求的类型
		if (CrowdUtil.judgeReqyestType(request)) {// 如果是ajax类型
			ResultEntity<Object> entity = ResultEntity.failed(exception.getMessage());// 创建对象
			Gson gson = new Gson();// 创建gson对象
			String json = gson.toJson(entity);// 将ResultEntity转换为字符串
			response.getWriter().write(json);// 将json数据作为相应体返回
			// 由于已经通过原生的response对象做了相应，所以不提供ModelAndView对象
			return null;
		}
		// 不是Ajax请求则先创建ModelAndView对象
		ModelAndView modelAndView = new ModelAndView();
		// 将异常存入ModelAndView
		modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
		// 指定相应的视图名称
		modelAndView.setViewName(viewName);
		return modelAndView;
	}

}
