package com.wxweven.utils;

/**
 * @author wxweven
 * @date 2014年9月18日
 * @version 1.0
 * @email wxweven@163.com
 * @blog http://wxweven.blog.163.com/
 * @Copyright: Copyright (c) wxweven 2014
 * @Description:常用的工具方法，都是静态的
 */
public class CommonUtils {

	/**
	 * 用指定的分隔符 split，将数组元素连接成字符串
	 * 
	 * @param obj
	 * @param split
	 * @return
	 */
	public static String arrayToString(Object[] obj, String split) {
		StringBuilder stringBuilder = new StringBuilder();
		// 遍历数组前 length-1 个元素, 最后一个元素单独处理
		for (int i = 0; i < obj.length - 1; i++) {
			stringBuilder.append(obj[i].toString() + split);
		}
		// 最后一个元素后不需要加 split
		stringBuilder.append(obj[obj.length - 1].toString());

		return stringBuilder.toString();
	}

	/**
	 * 将数组转换层list的String形式，另外对每个元素都加上引号
	 * 
	 * @param obj
	 *            需要被转化的数组
	 * @param quote
	 *            加上是引号，可以是单引号或者双引号
	 * @param split
	 *            分隔符
	 * @return
	 */
	public static String arrayToListWithQuote(Object[] obj, String quote, String split) {
		//边界判断很重要
		if(obj == null || obj.length == 0){
			return "[]";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");

		// 遍历数组前 length-1 个元素, 最后一个元素单独处理
		for (int i = 0; i < obj.length; i++) {
			if(i != 0){
				stringBuilder.append(split);
			}
			stringBuilder.append(quote + obj[i].toString() + quote);
		}
//		// 最后一个元素后不需要加 split
//		stringBuilder.append(quote + obj[obj.length - 1].toString() + quote);

		stringBuilder.append("]");

		return stringBuilder.toString();
	}

	public static Integer[] stringArr2IntArr(String[] strArr) {
		Integer[] intArr = new Integer[0];

		if (strArr == null || strArr.length == 0)
			return null;

		intArr = new Integer[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			intArr[i] = Integer.parseInt(strArr[i]);
		}

		return intArr;
	}

}
