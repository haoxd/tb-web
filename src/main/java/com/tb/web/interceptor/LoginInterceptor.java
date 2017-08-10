package com.tb.web.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tb.common.sys.util.CookieUtils;
import com.tb.sso.query.api.bean.TbUser;
import com.tb.web.service.user.TbUserService;
import com.tb.web.sys.contenst.ServiceCode;
import com.tb.web.thread.UserThreadLocal;

/**
 * @author acer11
 *  作者：
* 创建时间：2017年6月18日 下午1:58:24  
* 项目名称：tb-web  
* 文件名称：LoginInterceptor.java  
* 类说明：登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Resource(name="tbUserService")
	private TbUserService userService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//拦截器执行之后，首先清空本地线程当中user对象
		UserThreadLocal.set(null);
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* 登录前置方法
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		//进入拦截器，首先清空本地线程当中user对象
		UserThreadLocal.set(null);
		String loginToken = CookieUtils.getCookieValue(request, ServiceCode.token.LOGIN_COOKIE_TOKEN_NAME);
		//未登录重定向到登录页面
		if(StringUtils.isEmpty(loginToken)){
			response.sendRedirect(ServiceCode.token.LOGIN_PAGE);
			return false;
		}
		TbUser user = userService.queryUserByToken(loginToken);
		if(null==user ){
			String url=request.getRequestURI();
			response.sendRedirect(ServiceCode.token.LOGIN_PAGE);
			return false;	
		}
		//放入本地线程
		UserThreadLocal.set(user);
		return true;
	}

}
