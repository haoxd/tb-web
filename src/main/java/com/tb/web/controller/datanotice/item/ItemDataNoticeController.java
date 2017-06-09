package com.tb.web.controller.datanotice.item;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tb.common.service.cache.RedisCacheService;
import com.tb.web.controller.base.BaseController;
import com.tb.web.sys.contenst.redisConstenst.RedisConstant;


/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年6月9日 上午10:32:54  
* 项目名称：tb-web  
* 文件名称：ItemDataNoticeController.java  
* 类说明：商品数据通知接口（后台系统反调接口）
 */
@Controller
@RequestMapping("/ItemDataNotice")
public class ItemDataNoticeController extends BaseController {
	
	@Resource(name = "redis")
	private RedisCacheService redis;
	
	
	/**
	 * 后台系统通知前台系统商品同步（接受）
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/{itemId}",method=RequestMethod.POST,headers="HaiTaoWEB=1")
	public ResponseEntity<Void> itemDataSynchronization(@PathVariable("itemId") Long itemId){
		String redisKeyDetail = RedisConstant.REDISWEB.TB_WEB_ITEMDETAIL_REDIS_KEY+itemId;
		String redisKeydesc = RedisConstant.REDISWEB.TB_WEB_ITEMDESC_REDIS_KEY+itemId;
		String redisKeyParam = RedisConstant.REDISWEB.TB_WEB_ITEMPARAM_REDIS_KEY+itemId;
		String delKey[] ={redisKeyDetail,redisKeydesc,redisKeyParam};
		try {
			this.redis.del(delKey);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("收到后台系统数据更新通知删除缓存数据错误"+e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
