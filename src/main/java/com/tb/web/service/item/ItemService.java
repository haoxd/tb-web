package com.tb.web.service.item;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.bean.api.RespInfo;
import com.tb.manager.pojo.ItemDesc;
import com.tb.web.entity.Item;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;
import com.tb.web.util.xmlUtil.readAppXml;

@Service("itemService")
public class ItemService {
	
	
	/**
	 * http 服务
	 */
	@Resource(name = "apiServer")
	private HttpClientApiServerTools apiServer;

	private static final ObjectMapper Mapper = new ObjectMapper();
	
	
	/**
	 * 获取商品详情信息
	 * @param itemId
	 * @return
	 */
	public RespInfo getItemDetail(Long itemId){
		RespInfo resp = new RespInfo();
		try {
			RespInfo respInfo = this.apiServer.sendGet(readAppXml.readAppXMLByNode("item", "itemDetail")+itemId
					);
			RespInfo respItemDesc = this.apiServer.sendGet(readAppXml.readAppXMLByNode("item", "itemDesc")+itemId);
			
			if(HttpStatusCode.OK.equals(respInfo.getRespCode()) &&HttpStatusCode.OK.equals(respItemDesc.getRespCode()) ){
				HashMap<String, Object> data = new HashMap<String,Object>();
				resp.setRespCode(HttpStatusCode.OK);
				data.put("item", Mapper.readValue(respInfo.getData().toString(), Item.class));
				data.put("itemDesc", Mapper.readValue(respItemDesc.getData().toString(), ItemDesc.class));
				resp.setData(data);
			}else{
				resp.setRespCode(HttpStatusCode.NOTFOUND);
				resp.setRespDesc(respInfo.getRespDesc());
			}
		} catch (Exception e){
			resp.setRespCode(HttpStatusCode.NOTFOUND);
			resp.setRespDesc("获取商品详情错误："+e);
		}
		return resp;
		
	}

}
