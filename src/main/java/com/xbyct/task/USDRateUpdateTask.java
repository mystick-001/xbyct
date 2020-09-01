/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *      一米七                                       1.0.0        2020年9月1日 下午12:37:54
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xbyct.core.Rate;
import com.xbyct.util.JsonUtil;

/**
 * @author liu-guoping
 *
 */
@Component
public class USDRateUpdateTask {

	/**
	 * exchange rate api url
	 */
	private static final String EXCHANGE_RATE_API_URL = "https://open.exchangerate-api.com/v6/latest";

	/**
	 * update exchange rate of usd per 30 minute
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(fixedRate = 1800000)
	private void updateExchangeRate() {
		URLConnection connection = null;
		BufferedReader reader = null;
		try {
			StringBuilder sb = new StringBuilder();

			// connect to exchange rate api server
			connection = new URL(EXCHANGE_RATE_API_URL).openConnection();
			if (connection != null) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
			if (sb.length() <= 0) {
				return;
			}
			
			// format for api json result
			Map<String, Object> map = JsonUtil.mapFromJson(sb.toString(), String.class, Object.class);
			if (map != null && "success".equals(map.get("result")) && map.containsKey("rates")) {
				Map<String, Object> ratesMap = (Map<String, Object>) map.get("rates");
				if (ratesMap != null && !ratesMap.isEmpty()) {
					for (String key : ratesMap.keySet()) {
						Rate.setRate(key, "USD", Double.parseDouble(ratesMap.get(key).toString()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader = null;
			}
		}
	}
}
