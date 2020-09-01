/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *      一米七                                       1.0.0        2020年9月1日 上午10:08:44
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.xbyct.model.Record;

/**
 * @author liu-guoping
 *
 */
public class RecordManager {

	/**
	 * regex for pattern
	 */
	private static final String REGEX = "^[A-Z]{3}$";

	/**
	 * 币种交易记录表
	 */
	private Map<String, Record> recordMap = new ConcurrentHashMap<String, Record>();

	/**
	 * structor
	 */
	private RecordManager() {

	}

	/**
	 * get singleton instance
	 * 
	 * @return instance
	 */
	public static RecordManager getInstance() {
		return PaymentRecordManagerHoler.instance;
	}

	/**
	 * static class for singleton
	 */
	private static class PaymentRecordManagerHoler {
		public static RecordManager instance = new RecordManager();
	}

	/**
	 * explain command line to record
	 * 
	 * @param str
	 * @return
	 */
	public void explainLine(String str) {
		String[] arr = str.trim().split(" ");
		if (arr.length == 2 && Pattern.matches(REGEX, arr[0])) {
			try {
				boolean bool = this.add(arr[0], Double.parseDouble(arr[1]));
				if (!bool) {
					System.out.println("Input illegal,the balance is not enough：" + str);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Input illegal：" + str);
		}
	}

	/**
	 * record of payments
	 * 
	 * @param from   currency
	 * @param amount
	 */
	private boolean add(String from, double amount) {
		Record pr = this.recordMap.get(from);
		if (pr == null) {
			pr = new Record(from, 0);
			this.recordMap.put(from, pr);
		}
		return pr.pay(amount);
	}

	/**
	 * display list of records
	 */
	public List<String> getOutputList() {
		List<String> list = new ArrayList<String>();
		if (!this.recordMap.isEmpty()) {
			for (Map.Entry<String, Record> entry : this.recordMap.entrySet()) {
				String str = entry.getValue().toUSDString();
				if (!"".equals(str)) {
					list.add(str);
				}
			}
		}
		return list;
	}
}
