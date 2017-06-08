package com.tb.web.sys.contenst.redisConstenst;

/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年5月10日 下午9:37:48  
* 项目名称：tb-manager-service  
* 文件名称：RedisConstant.java  
* 类说明：redis 静态私有常量定义
 */
public class RedisConstant {
	
	/*
	 * 前台对应后台的缓存键
	 * */
	public interface REDISWEB{
		
		/**
		 *商品主信息(缓存)
		 */
		String TB_WEB_ITEMDETAIL_REDIS_KEY="RedisItemDetail";
		
		/**
		 *商品描述(缓存)
		 */
		String TB_WEB_ITEMDESC_REDIS_KEY="RedisItemDesc";
		
		/**
		 *商品规格(缓存)
		 */
		String TB_WEB_ITEMPARAM_REDIS_KEY="RedisItemParam";
		
		/**
		 * 商品存在时间(缓存) 
		 */
		Integer TB_WEB_ITEM_REDIS_KEY_SECONDS=60*60*24;
		
		
	}

}
