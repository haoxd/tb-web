package com.tb.web.sys.contenst;

public class ServiceCode {
	
	public interface token{

		 String LOGIN_COOKIE_TOKEN_NAME="SSO_TOKEN";
		 
		 String LOGIN_PAGE="http://sso.tb.com/user/login.html";
	 }
	
	public interface order{
		String ORDER_STATUS="status";
		String ORDER_SUBMIT_STATUS_SUCC="200";
		String ORDER_SUBMIT_STATUS_FAIL="500";
		String ORDER_DATA="data";
		//业务状态
		Integer ORDER_SERVICE_CODE=200;
	}
}
