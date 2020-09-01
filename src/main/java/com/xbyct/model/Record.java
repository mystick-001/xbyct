/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *      一米七                                       1.0.0        2020年9月1日 上午8:24:27
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct.model;

import java.math.BigDecimal;

import com.xbyct.core.Rate;

/**
 * java object record of payments
 * 
 * @author liu-guoping
 *
 */
public class Record {

	/**
	 * the system of money that a country uses
	 */
	private String currency;

	/**
	 * a sum of money
	 */
	private BigDecimal amount;

	public Record(String currency, long value) {
		this.currency = currency;
		this.amount = BigDecimal.valueOf(value);
	}

	/**
	 * do record
	 * 
	 * @param value transaction amount
	 */
	public synchronized boolean pay(double value) {
		// balance is not enough and return false
		if (value < 0 && this.amount.compareTo(BigDecimal.valueOf(-value)) < 0) {
			return false;
		}
		this.amount = amount.add(BigDecimal.valueOf(value));
		return true;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 * @return the format output record of payment
	 */
	public String toUSDString() {
		StringBuffer sb = new StringBuffer();
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			sb.append(this.currency);
			sb.append(" ");
			sb.append(amount.setScale(2).stripTrailingZeros().toPlainString());

			// get exchange rate compared to usd
			double rate = Rate.getToUSDRate(this.currency);
			if (!"USD".equals(this.currency) && rate > 0) {
				sb.append("(");
				sb.append(amount.divide(new BigDecimal(rate), 2, BigDecimal.ROUND_HALF_UP).toPlainString());
				sb.append(")");
			}
		}
		return sb.toString();
	}

}
