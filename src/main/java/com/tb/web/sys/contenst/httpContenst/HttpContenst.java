package com.tb.web.sys.contenst.httpContenst;

/**
 * @author acer11 作者：haoxud 创建时间：2017年3月26日 下午7:19:26 项目名称：tb-web
 * @author daniel
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：HttpContenst.java 类说明：http接口返回静态类
 */
public class HttpContenst {
		/*
		 * http报文信息
		 * **/
	public interface httpInfo {

		public static final String RESP_HTTP_STATUS = "respHttpStatus";// 状态码

		public static final String RESP_DESC = "respDesc";// 报文信息

		public static final String RESP_DATA = "respData";// 报文数据

		public static final String RESP_CODE = "respCode";// 失败

	}
	/*
	 * http 访问路径
	 * **/
	public interface URL {

		public static final String PREFIX = "http://tb.manager.com/ow/api/"; //接口前缀

	}
	
	public interface HttpStatusCode {

		public static final String NOTFOUND = "404"; 
		public static final String SERVERERROR = "500";
		public static final String OK = "200";

	}
	
	
	
}
