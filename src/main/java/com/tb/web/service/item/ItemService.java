package com.tb.web.service.item;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tb.common.bean.api.RespInfo;
import com.tb.common.service.cache.RedisCacheService;
import com.tb.manager.pojo.ItemDesc;
import com.tb.manager.pojo.ItemParamItem;
import com.tb.web.entity.Item;
import com.tb.web.sys.contenst.httpContenst.HttpConstants.HttpStatusCode;
import com.tb.web.sys.contenst.redisConstenst.RedisConstant;
import com.tb.web.sys.httplient.ws.server.intf.HttpClientApiServerTools;
import com.tb.web.util.xmlUtil.readAppXml;

@Service("itemService")
public class ItemService {

	private static final Logger log = LoggerFactory.getLogger(ItemService.class);

	/**
	 * http 服务
	 */
	@Resource(name = "apiServer")
	private HttpClientApiServerTools apiServer;

	@Resource(name = "redis")
	private RedisCacheService redis;

	private static final ObjectMapper Mapper = new ObjectMapper();

	/**
	 * 获取商品详情信息
	 * 
	 * @param itemId
	 * @return
	 */
	public RespInfo getItemDetail(Long itemId) {
		RespInfo resp = new RespInfo();
		HashMap<String, Object> data = new HashMap<String, Object>();
		String redisKey  =RedisConstant.REDISWEB.TB_WEB_ITEMDETAIL_REDIS_KEY + itemId;
		try {
			String itemDetailCacheData = this.redis.get(redisKey);
			if (null != itemDetailCacheData && StringUtils.isNotEmpty(itemDetailCacheData)) {
				data.put("item", Mapper.readValue(itemDetailCacheData, Item.class));
				resp.setRespCode(HttpStatusCode.OK);
				resp.setData(data);
				return resp;
			}
		} catch (Exception e1) {
			log.error("前台缓存获取商品信息错误：" + e1);
		}
		try {
			RespInfo respInfo = this.apiServer.sendGet(readAppXml.readAppXMLByNode("item", "itemDetail") + itemId);

			if (HttpStatusCode.OK.equals(respInfo.getRespCode())) {
				resp.setRespCode(HttpStatusCode.OK);
				data.put("item", Mapper.readValue(respInfo.getData().toString(), Item.class));
				resp.setData(data);
			
				try {
					this.redis.set(redisKey, respInfo.getData().toString(), RedisConstant.REDISWEB.TB_WEB_ITEM_REDIS_KEY_SECONDS);
				} catch (Exception e) {
					log.error("前台缓存保存商品信息错误：" + e);
				}
			
			} else {
				resp.setRespCode(HttpStatusCode.NOTFOUND);
				resp.setRespDesc(respInfo.getRespDesc());
			}
		} catch (Exception e) {
			resp.setRespCode(HttpStatusCode.NOTFOUND);
			resp.setRespDesc("获取商品详情错误：" + e);
		}
		return resp;

	}

	/**
	 * 获取商品描述数据
	 * @param itemId
	 * @return
	 */
	public RespInfo getItemDesc(Long itemId) {
		RespInfo resp = new RespInfo();
		HashMap<String, Object> data = new HashMap<String, Object>();
		String redisKey = RedisConstant.REDISWEB.TB_WEB_ITEMDESC_REDIS_KEY + itemId;
		try {
			String itemDescCacheData = this.redis.get(redisKey);
			if (null != itemDescCacheData && StringUtils.isNotEmpty(itemDescCacheData)) {
				data.put("itemDesc", Mapper.readValue(itemDescCacheData, ItemDesc.class));
				resp.setRespCode(HttpStatusCode.OK);
				resp.setData(data);
				return resp;
			}
		} catch (Exception e1) {
			log.error("前台缓存获取商品描述信息错误：" + e1);
		}
		try {

			RespInfo respItemDesc = this.apiServer.sendGet(readAppXml.readAppXMLByNode("item", "itemDesc") + itemId);

			if (HttpStatusCode.OK.equals(respItemDesc.getRespCode())) {
				resp.setRespCode(HttpStatusCode.OK);
				data.put("itemDesc", Mapper.readValue(respItemDesc.getData().toString(), ItemDesc.class));
				resp.setData(data);

				try {
					this.redis.set(redisKey, respItemDesc.getData().toString(), RedisConstant.REDISWEB.TB_WEB_ITEM_REDIS_KEY_SECONDS);
				} catch (Exception e) {
					log.error("前台缓存保存描述信息错误：" + e);
				}
			} else {
				resp.setRespCode(HttpStatusCode.NOTFOUND);
				resp.setRespDesc(respItemDesc.getRespDesc());
			}
		} catch (Exception e) {
			resp.setRespCode(HttpStatusCode.NOTFOUND);
			resp.setRespDesc("获取商品描述信息错误：" + e);
		}
		return resp;

	}

	/**
	 * 获取商品规格数据
	 * @param itemId
	 * @return
	 */
	public RespInfo getItemParam(Long itemId) {
		RespInfo resp = new RespInfo();
		HashMap<String, Object> data = new HashMap<String, Object>();
		String redisKey =RedisConstant.REDISWEB.TB_WEB_ITEMPARAM_REDIS_KEY+ itemId;
		try {
			String itemParamCacheData = this.redis.get(redisKey);
			if (null != itemParamCacheData && StringUtils.isNotEmpty(itemParamCacheData)) {
				data.put("itemParam", itemParamCacheData);
				resp.setRespCode(HttpStatusCode.OK);
				resp.setData(data);
				return resp;
			}
		} catch (Exception e1) {
			log.error("前台缓存获取商品规格错误：" + e1);
		}
		try {

			RespInfo respinfo = this.apiServer.sendGet(readAppXml.readAppXMLByNode("item", "itemParam") + itemId);

			if (HttpStatusCode.OK.equals(respinfo.getRespCode())) {

				resp.setRespCode(HttpStatusCode.OK);
				String createItemParamData = createItemParamData(
						Mapper.readValue(respinfo.getData().toString(), ItemParamItem.class).getParamData());
				data.put("itemParam", createItemParamData);
				resp.setData(data);
				try {
					this.redis.set(redisKey,createItemParamData, RedisConstant.REDISWEB.TB_WEB_ITEM_REDIS_KEY_SECONDS);
				} catch (Exception e) {
					log.error("前台缓存保存商品规格错误：" + e);
				}
			} else {
				resp.setRespCode(HttpStatusCode.NOTFOUND);
				resp.setRespDesc(respinfo.getRespDesc());
			}
		} catch (Exception e) {
			resp.setRespCode(HttpStatusCode.NOTFOUND);
			resp.setRespDesc("获取商品规格错误：" + e);
		}
		return resp;

	}

	/**
	 * 将商品规格参数序列化成html
	 * 
	 * @param paramData
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private static String createItemParamData(String paramData) throws JsonProcessingException, IOException {
		ArrayNode arrayNode = (ArrayNode) Mapper.readTree(paramData);
		StringBuilder sb = new StringBuilder();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
		for (JsonNode param : arrayNode) {
			sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText() + "</th></tr>");
			ArrayNode params = (ArrayNode) param.get("params");
			for (JsonNode p : params) {
				sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>" + p.get("v").asText()
						+ "</td></tr>");
			}
		}
		sb.append("</tbody></table>");
		return sb.toString();
	}

}
