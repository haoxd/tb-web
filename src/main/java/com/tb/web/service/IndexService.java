package com.tb.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tb.common.bean.api.RespInfo;
import com.tb.web.sys.contenst.httpContenst.HttpContenst;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;

@Service
public class IndexService {
	
	
	private static final String REQUERT_INDEX_BIGAD_URL="content/?categoryId=4&page=1&rows=6"; //请求大广告路径
	
	/**
	 * http 服务
	 */
	@Resource(name="apiServer")
	private HttpClientApiServerTools apiServer;
	
	private static final ObjectMapper Mapper  = new ObjectMapper();

	/**
	 * 获取主页大广告
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public RespInfo queryIndexBigAd() {
		RespInfo resp = new RespInfo();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();	 
		try {
			RespInfo respInfo = this.apiServer.sendGet(REQUERT_INDEX_BIGAD_URL);
			if(HttpContenst.HttpStatusCode.OK.equals(respInfo.getRespCode())){
				JsonNode jsonNode = Mapper.readTree(respInfo.getData().toString());
				ArrayNode rowsArray = (ArrayNode) jsonNode.get("rows");
				for (JsonNode rows : rowsArray) {
					Map<String ,Object> map = new LinkedHashMap<String,Object>();//为保证和前端要求的顺序一致
					map.put("srcB",rows.get("pic").asText());
					map.put("height",240);
					map.put("alt",rows.get("title").asText() );
					map.put("width",670 );
					map.put("src",rows.get("pic").asText());
					map.put("widthB",550);
					map.put("href",rows.get("url").asText() );
					map.put("heightB",20);
					data.add(map);
				}
				resp.setData(Mapper.writeValueAsString(data));//序列化为JSON字符串
				resp.setRespCode(HttpContenst.HttpStatusCode.OK);
				
			}else{
				resp.setRespDesc(respInfo.getRespDesc());
				resp.setRespCode(HttpContenst.HttpStatusCode.SERVERERROR);
			}
		} catch (Exception e) {
			resp.setRespDesc("请求大广告服务错误"+e);
			resp.setRespCode(HttpContenst.HttpStatusCode.SERVERERROR);
		}
		
		return resp;
	}

}
