package com.wxweven.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonLibDateProcessor implements JsonValueProcessor {
	/** 供调用的 static 实例 */
	public static final JsonLibDateProcessor instance = new JsonLibDateProcessor();

	private String format = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat sdf = new SimpleDateFormat(format);

	public Object processObjectValue(String key, Object value, JsonConfig jc) {
		
		//1. 过滤 Date 类型
		if (value == null) {
			return "";
		} else if (value instanceof Date)
			return sdf.format((Date) value); //格式化日期字符串
		
		return value.toString();
	}

	public Object processArrayValue(Object value, JsonConfig arg1) {
		return null;
	}

}


