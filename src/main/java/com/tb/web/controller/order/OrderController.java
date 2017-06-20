package com.tb.web.controller.order;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tb.common.bean.api.RespInfo;
import com.tb.web.entity.Order;
import com.tb.web.service.item.ItemService;
import com.tb.web.service.order.OrderService;
import com.tb.web.service.user.TbUserService;
import com.tb.web.sys.contenst.ServiceCode;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;

@RequestMapping("/order")
@Controller
public class OrderController {

	@Resource(name = "itemService")
	private ItemService itemService;

	@Resource(name = "orderService")
	private OrderService orderService;

	/**
	 * 订单确认页
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/isOkOrderInfo/{itemId}", method = RequestMethod.GET)
	public ModelAndView isOkOrderInfo(@PathVariable("itemId") Long itemId) {
		ModelAndView modelAndView = new ModelAndView("order");
		RespInfo resp = this.itemService.getItemDetail(itemId);
		if (HttpStatusCode.OK.equals(resp.getRespCode())) {
			HashMap<String, Object> dataDetail = (HashMap<String, Object>) resp.getData();

			modelAndView.addObject("item", dataDetail.get("item"));
		}
		return modelAndView;

	}

	/**
	 * 订单正式提交
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/orderSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orderSubmit(Order order) {
		Map<String, Object> resp = new HashMap<String, Object>();

		if (null == order) {
			resp.put(ServiceCode.order.ORDER_STATUS, ServiceCode.order.ORDER_SUBMIT_STATUS_FAIL);
			resp.put(ServiceCode.order.ORDER_DATA, "提交参数有误");
			return resp;
		}

		try {
			String orderId = this.orderService.orderSubmit(order);

			if (StringUtils.isEmpty(orderId)) {
				resp.put(ServiceCode.order.ORDER_STATUS, ServiceCode.order.ORDER_SUBMIT_STATUS_FAIL);
				resp.put(ServiceCode.order.ORDER_DATA, "订单id为空");
			} else {
				resp.put(ServiceCode.order.ORDER_STATUS, ServiceCode.order.ORDER_SUBMIT_STATUS_SUCC);
				resp.put(ServiceCode.order.ORDER_DATA, orderId);
			}
		} catch (Exception e) {
			resp.put(ServiceCode.order.ORDER_STATUS, ServiceCode.order.ORDER_SUBMIT_STATUS_FAIL);
			resp.put(ServiceCode.order.ORDER_DATA, e.toString());
		}

		return resp;

	}
	
	/**
	 * 订单成功页面
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value="/success",method=RequestMethod.GET)
	public ModelAndView orderSuccess(@RequestParam("id")String orderId){
		ModelAndView modelAndView = new ModelAndView("success");
		try {
			//订单数据
		Order order=	this.orderService.queryOrderInfoByOrderId(orderId);
		if(null != order){
			modelAndView.addObject("order", order);
			//送货时间，当前时间加俩天
			modelAndView.addObject("date", new DateTime().plusDays(2).toString("MM月dd日") );
		}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}
	

}
