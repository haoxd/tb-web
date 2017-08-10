package com.tb.web.service.cart;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.bean.api.RespInfo;
import com.tb.web.entity.Cart;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;
import com.tb.web.thread.UserThreadLocal;

@Service("cartService")
public class CartService {
	

	/**
	 * http 服务
	 */
	@Resource(name = "apiServer")
	private HttpClientApiServerTools http;
	
	@Value("${MY_CART_LIST_URL}")
	private String MY_CART_LIST_URL;
	
	private static final ObjectMapper oMapper = new ObjectMapper();
	
	
	public List<Cart> queryMyCartList(String itemId){
		Map<String,Object> inParam = new HashMap<String,Object>();
		inParam.put("itemId", itemId);
		try {
		RespInfo resp=	this.http.sendGetNoReadXML(MY_CART_LIST_URL+UserThreadLocal.get().getUserId()+"?itemId="+itemId);
			if(HttpStatusCode.OK.equals(resp.getRespCode())){
				List<Cart> cartList = oMapper.readValue(resp.getData().toString(), oMapper
						.getTypeFactory().constructCollectionType(List.class, Cart.class)); 
				return cartList;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
