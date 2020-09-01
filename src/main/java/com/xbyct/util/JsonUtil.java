/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *   liu-guoping       1.0.0        2020年9月1日 上午10:24:48
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Function: jackson json 处理工具类
 *
 * Author：liu-guoping
 * 
 * Time：2020年9月1日 上午10:24:48
 */
public abstract class JsonUtil {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * Json字符串转换为Map对象
	 * 
	 * @param <H>        Key类型
	 * @param <K>        Value类型
	 * @param json       Json字符串
	 * @param keyClazz   Key类
	 * @param valueClazz value类
	 * @return Map对象
	 */
	public static <H, K> Map<H, K> mapFromJson(String json, Class<H> keyClazz, Class<K> valueClazz) {
		try {
			ObjectMapper mapper = getMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, keyClazz, valueClazz);

			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			log.error("json字符串转换成集合对象错误：json={},keyClazz={},valueClazz={},error={}", json, keyClazz, valueClazz, e);
		}
		return null;
	}

	/**
	 * 获取json转换对象
	 * 
	 * @return
	 */
	public static ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		// 忽略空属性
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		// 忽略对象中没有的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		return mapper;
	}

}
