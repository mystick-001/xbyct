/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *      一米七                                       1.0.0        2020年9月1日 上午9:25:11
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liu-guoping
 *
 */
public abstract class Rate {

	/**
	 * key: $from-$to , value: $rate
	 */
	private static Map<String, Double> RATE_MAP = new ConcurrentHashMap<String, Double>();

	/**
	 * the exchange rate to set
	 * 
	 * @param from target currency
	 * @param to   compared to currency
	 * @param rate exchange rate
	 */
	public static void setRate(String from, String to, double rate) {
		RATE_MAP.put(from + "-" + to, rate);
	}

	/**
	 * get from exchange rate compared to to
	 * 
	 * @param from target currency
	 * @param to   compared to currency
	 * @return exchange rate
	 */
	public static double getRate(String from, String to) {
		String key = from + "-" + to;
		if (RATE_MAP.containsKey(key)) {
			return RATE_MAP.get(key);
		}
		return -1;
	}

	/**
	 * get from exchange rate compared to usd
	 * 
	 * @param from from target currency
	 * @return exchange rate
	 */
	public static double getToUSDRate(String from) {
		return getRate(from, "USD");
	}
}
