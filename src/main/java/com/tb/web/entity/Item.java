package com.tb.web.entity;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.tb.manager.pojo.Item{
	
	/**
	 * 分割图片json为图片数组
	 * @return
	 */
	public String [] getImages(){
		return StringUtils.split(super.getImage(), ",");
	}

}
