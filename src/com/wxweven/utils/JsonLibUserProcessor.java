package com.wxweven.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonLibUserProcessor implements JsonValueProcessor {
	/** 供调用的 static 实例 */
	public static final JsonLibUserProcessor instance = new JsonLibUserProcessor();
	
	public Object processObjectValue(String key, Object value, JsonConfig jc) {
		
		if (value == null) {
			return "";
		} else if(key.equals("userState")){//对 userState 属性过滤
			
			if(value.equals("可用"))
				return 1; 
			else
				return 0;
		}
		
		return value.toString();
	}

	public Object processArrayValue(Object value, JsonConfig arg1) {
		return null;
	}

}


