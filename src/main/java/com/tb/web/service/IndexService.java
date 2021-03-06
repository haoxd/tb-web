package com.tb.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tb.common.bean.EasyUIResult;
import com.tb.common.bean.api.RespInfo;
import com.tb.manager.pojo.Content;
import com.tb.web.sys.contenst.httpContenst.HttpConstants;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;
import com.tb.web.util.xmlUtil.readAppXml;

@Service("indexService")
public class IndexService {

	/**
	 * http 服务
	 */
	@Resource(name = "apiServer")
	private HttpClientApiServerTools apiServer;

	private static final ObjectMapper Mapper = new ObjectMapper();

	/**
	 * 获取主页大广告
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public RespInfo queryIndexBigAd() {
		RespInfo resp = new RespInfo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", "4");
		params.put("page", "1");
		params.put("rows", "6");
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {
			// this.apiServer.sendGet(readAppXml.readAppXMLByNode("index",
			// "indexPage"));
			RespInfo respInfo = this.apiServer.sendGetByParam(readAppXml.readAppXMLByNode("index", "indexPage"),
					params);
			if (HttpConstants.HttpStatusCode.OK.equals(respInfo.getRespCode())) {
				JsonNode jsonNode = Mapper.readTree(respInfo.getData().toString());
				ArrayNode rowsArray = (ArrayNode) jsonNode.get("rows");
				for (JsonNode rows : rowsArray) {
					Map<String, Object> map = new LinkedHashMap<String, Object>();// 为保证和前端要求的顺序一致
					map.put("srcB", rows.get("pic").asText());
					map.put("height", 240);
					map.put("alt", rows.get("title").asText());
					map.put("width", 670);
					map.put("src", rows.get("pic").asText());
					map.put("widthB", 550);
					map.put("href", rows.get("url").asText());
					map.put("heightB", 20);
					data.add(map);
				}
				resp.setData(Mapper.writeValueAsString(data));// 序列化为JSON字符串
				resp.setRespCode(HttpConstants.HttpStatusCode.OK);

			} else {
				resp.setRespDesc(respInfo.getRespDesc());
				resp.setRespCode(HttpConstants.HttpStatusCode.SERVERERROR);
			}
		} catch (Exception e) {
			resp.setRespDesc("请求大广告服务错误" + e);
			resp.setRespCode(HttpConstants.HttpStatusCode.SERVERERROR);
		}

		return resp;
	}

	/**
	 * 获取主页小广告(操作json对象)
	 * 
	 * @return
	 */
	public RespInfo queryIndexMinAd() {

		RespInfo resp = new RespInfo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", "5");
		params.put("page", "1");
		params.put("rows", "1");
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {			
			RespInfo respInfo = this.apiServer.sendGetByParam(readAppXml.readAppXMLByNode("index", "indexPage"),
					params);
			if (HttpConstants.HttpStatusCode.OK.equals(respInfo.getRespCode())) {
				JsonNode jsonNode = Mapper.readTree(respInfo.getData().toString());
				ArrayNode rowsArray = (ArrayNode) jsonNode.get("rows");
				for (JsonNode rows : rowsArray) {

					Map<String, Object> map = new LinkedHashMap<String, Object>();// 为保证和前端要求的顺序一致
					map.put("width", 310);
					map.put("height", 70);
					map.put("src", rows.get("pic").asText());
					map.put("href", rows.get("url").asText());
					map.put("alt", rows.get("title").asText());
					map.put("widthB", 210);
					map.put("heightB", 70);
					map.put("srcB", rows.get("pic").asText());

					data.add(map);
				}
				resp.setData(Mapper.writeValueAsString(data));// 序列化为JSON字符串
				resp.setRespCode(HttpConstants.HttpStatusCode.OK);

			} else {
				resp.setRespDesc(respInfo.getRespDesc());
				resp.setRespCode(HttpConstants.HttpStatusCode.SERVERERROR);
			}
		} catch (Exception e) {
			resp.setRespDesc("请求小广告服务错误" + e);
			resp.setRespCode(HttpConstants.HttpStatusCode.SERVERERROR);
		}

		return resp;

	}
	
	
	/**
	 * 获取主页小广告（类对象操作）
	 * 
	 * 
	 * @return
	 */
	public RespInfo queryIndexMinAdByClazz() {

		RespInfo resp = new RespInfo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryId", "5");
		params.put("page", "1");
		params.put("rows", "1");
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		try {			
			RespInfo respInfo = this.apiServer.sendGetByParam(readAppXml.readAppXMLByNode("index", "indexPage"),
					params);
			if (HttpConstants.HttpStatusCode.OK.equals(respInfo.getRespCode())) {
				EasyUIResult easyUIResult = EasyUIResult.formatToList(respInfo.getData().toString(), Content.class);
				
				List<Content> contentList =  (List<Content>) easyUIResult.getRows();
				for (Content content : contentList) {

					Map<String, Object> map = new LinkedHashMap<String, Object>();// 为保证和前端要求的顺序一致
					map.put("width", 310);
					map.put("height", 70);
					map.put("src", content.getPic());
					map.put("href", content.getUrl());
					map.put("alt", content.getTitle());
					map.put("widthB", 210);
					map.put("heightB", 70);
					map.put("srcB",  content.getPic());

					data.add(map);
				}
				resp.setData(Mapper.writeValueAsString(data));// 序列化为JSON字符串 输出
				resp.setRespCode(HttpConstants.HttpStatusCode.OK);

			} else {
				resp.setRespDesc(respInfo.getRespDesc());
				resp.setRespCode(HttpConstants.HttpStatusCode.SERVERERROR);
			}
		} catch (Exception e) {
			resp.setRespDesc("请求小广告服务错误" + e);
			resp.setRespCode(HttpConstants.HttpStatusCode.SERVERERROR);
		}

		return resp;

	}

}
