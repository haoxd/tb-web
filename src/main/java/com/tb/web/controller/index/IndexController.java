package com.tb.web.controller.index;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tb.web.controller.base.BaseController;


/**
 * @author acer11
 *  作者：
* 创建时间：2017年2月24日 下午3:18:26  
* 项目名称：tb-web  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：IndexController.java  
* 类说明：首页访问controller
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	
	/**
	 * 返回首页的模型数据和页面信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getIndexPageInfo(){
		 ModelAndView modelAndView = new ModelAndView("index");
		
		return modelAndView;
	}

	
	
}
