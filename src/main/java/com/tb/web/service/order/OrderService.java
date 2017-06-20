package com.tb.web.service.order;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.bean.api.RespInfo;
import com.tb.web.entity.Order;
import com.tb.web.entity.TbUser;
import com.tb.web.service.user.TbUserService;
import com.tb.web.sys.contenst.ServiceCode;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;
import com.tb.web.thread.UserThreadLocal;

@Service("orderService")
public class OrderService {

	/**
	 * http 服务
	 */
	@Resource(name = "apiServer")
	private HttpClientApiServerTools http;

	@Value("${TB_ORDER_CREATEORDER_URL}")
	private String TB_ORDER_CREATEORDER_URL;

	@Value("${TB_ORDER_GETORDERINFO_URL}")
	private String TB_ORDER_GETORDERINFO_URL;

	private static final ObjectMapper oMapper = new ObjectMapper();

	/**
	 * 提交订单
	 * 
	 * @param order
	 * @return
	 */
	public String orderSubmit(Order order) {
		try {
			// 填加用户信息
			TbUser user = UserThreadLocal.get();
			order.setUserId(user.getUserId());
			order.setBuyerNick(user.getUserName());
			RespInfo resp = this.http.sendPostJSON(TB_ORDER_CREATEORDER_URL, oMapper.writeValueAsString(order));

			if (HttpStatusCode.OK.equals(resp.getRespCode())) {
				JsonNode jsonNode = oMapper.readTree((String) resp.getData());
				if (ServiceCode.order.ORDER_SERVICE_CODE == jsonNode.get("status").asInt()) {
					return jsonNode.get("data").asText();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 */
	public Order queryOrderInfoByOrderId(String orderId) {

		try {
			RespInfo resp = this.http.sendGetNoReadXML(TB_ORDER_GETORDERINFO_URL + orderId);
			if (HttpStatusCode.OK.equals(resp.getRespCode())) {
				String orderData = (String) resp.getData();
				if (StringUtils.isNotEmpty(orderData)) {
					return oMapper.readValue(orderData, Order.class);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
