package com.edu.crowd.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.exception.AccessForbiddenException;
import com.edu.crowd.entity.Admin;

/**
 * @author wyg_edu
 * @date 2020年5月10日 下午4:08:01
 * @version v1.0
 * HandlerInterceptorAdapter 只有前置拦截
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1、通过request对象获取session
		HttpSession httpSession = request.getSession();
		// 2、尝试从session域中获取Admin对象
		Admin admin = (Admin) httpSession.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
		// 3、判断Admin对象是否为空
		if(admin == null) {
			// 4、抛出自定义异常
			throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
		}
		// 5、如果admin对象不为空，则通过
		return true;
	}
}
