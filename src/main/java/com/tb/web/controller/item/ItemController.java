package com.tb.web.controller.item;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tb.common.bean.api.RespInfo;
import com.tb.web.controller.base.BaseController;
import com.tb.web.entity.Item;
import com.tb.web.service.item.ItemService;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;


@Controller
@RequestMapping("item")
public class ItemController  extends BaseController {
	
	@Resource(name="itemService")
	private ItemService itemService;
	
	/**
	 *获取商品详情
	 *@param itemId
	 *@return
	 */
	@RequestMapping(value="{itemId}", method= RequestMethod.GET)
	public ModelAndView getItemDetail(@PathVariable("itemId") Long itemId){
		ModelAndView modelAndView = new ModelAndView("item");
		
		RespInfo resp = this.itemService.getItemDetail(itemId);
		
		HashMap<String, Object> data = (HashMap<String, Object>) resp.getData();
		
		if(HttpStatusCode.OK.equals(resp.getRespCode())){
			 modelAndView.addObject("item", data.get("item"));
			 modelAndView.addObject("itemDesc", data.get("itemDesc"));
		}
		return modelAndView;
	}
}
