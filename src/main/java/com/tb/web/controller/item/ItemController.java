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
		
		try {
			RespInfo respDetail = this.itemService.getItemDetail(itemId);		
			HashMap<String, Object> dataDetail = (HashMap<String, Object>) respDetail.getData();
			
			RespInfo respDesc = this.itemService.getItemDesc(itemId);	
			HashMap<String, Object> dataDesc = (HashMap<String, Object>) respDesc.getData();
			
			RespInfo respItemParam = this.itemService.getItemParam(itemId);	
			HashMap<String, Object> dataItemParam = (HashMap<String, Object>) respItemParam.getData();
			
			if(HttpStatusCode.OK.equals(respDetail.getRespCode())){
				 modelAndView.addObject("item", dataDetail.get("item"));
				
				
			}
			if(HttpStatusCode.OK.equals(respDesc.getRespCode())){
				 modelAndView.addObject("itemDesc", dataDesc.get("itemDesc"));
			}
			
			if(HttpStatusCode.OK.equals(respItemParam.getRespCode())){
				 modelAndView.addObject("itemParam", dataItemParam.get("itemParam"));
			}
			return modelAndView;
		} catch (Exception e) {
			return new ModelAndView("item");
		}
		
	}
}
