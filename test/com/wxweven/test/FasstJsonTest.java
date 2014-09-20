package com.wxweven.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FasstJsonTest {

	@Test
	public void testFastJson() {
		String text = "{'name':'老张头', 'age':66}";

		//1. 把JSON文本parse为JSONObject或者JSONArray
		Object user = JSON.parse(text);
		System.out.println(user);
		
		

	}

}
