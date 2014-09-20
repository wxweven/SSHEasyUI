package com.wxweven.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author wxweven
 * @date 2014年9月19日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:
 */
public class BasicPriv {

	public static String[] basicPrivs = new String[]{
			"/home_frame",
			"/home_welcome",
			"/user_logout",
			"/user_menu"
	};
	
//	public static List<String> basicPrivList = new ArrayList<String>();
	
	public static List<String> getBasicPrivList(){
		return Arrays.asList(basicPrivs);
	}
}
