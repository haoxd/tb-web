package com.tb.web.controller.index;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tb.common.bean.api.RespInfo;
import com.tb.web.controller.base.BaseController;
import com.tb.web.service.IndexService;
import com.tb.web.sys.contenst.httpContenst.HttpContenst;


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
	 * 主页服务
	 */
	@Resource(name="indexService")
	private IndexService indexService;
	
	
	/**
	 * 返回首页的模型数据和页面信息
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getIndexPageInfo(){
		 ModelAndView modelAndView = new ModelAndView("index");
		
		 RespInfo  info = this.indexService.queryIndexBigAd(); 
		 if(HttpContenst.HttpStatusCode.OK.equals(info.getRespCode())){
			 modelAndView.addObject("indexBigAd", info.getData());
		 }		 
		return modelAndView;
	}

	
	
}
