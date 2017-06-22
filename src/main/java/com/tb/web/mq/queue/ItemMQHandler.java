package com.tb.web.mq.queue;

import java.io.IOException;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.common.service.cache.RedisCacheService;
import com.tb.web.sys.contenst.redisConstenst.RedisConstant;

/**
 * @author acer11 作者： 创建时间：2017年6月22日 下午2:46:13 项目名称：tb-web
 *         文件名称：ItemMQHandler.java 类说明：前台系统item消费者实现
 */
public class ItemMQHandler {

	/**
	 * redis服务
	 */
	@Resource(name = "redis")
	private RedisCacheService redis;

	private static final ObjectMapper oMapper = new ObjectMapper();

	/**
	 * 删除缓存当中的数据，完成商品信息的同步
	 * 
	 * @param MSG
	 */
	public void execute(String MSG) {

		try {
			JsonNode jsonNode = oMapper.readTree(MSG);
			Long itemId = jsonNode.get("itemId").asLong();
			String redisKeyDetail = RedisConstant.REDISWEB.TB_WEB_ITEMDETAIL_REDIS_KEY + itemId;
			String redisKeydesc = RedisConstant.REDISWEB.TB_WEB_ITEMDESC_REDIS_KEY + itemId;
			String redisKeyParam = RedisConstant.REDISWEB.TB_WEB_ITEMPARAM_REDIS_KEY + itemId;
			String delKey[] = { redisKeyDetail, redisKeydesc, redisKeyParam };
			this.redis.del(delKey);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
