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

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xbyct.core.RecordManager;

/**
 * @author liu-guoping
 *
 */
@Component
public class RecordDisplayTask {

	/**
	 * output all the currency and amounts to the console once per minute
	 */
	@Scheduled(fixedRate = 60000)
	private void display() {
		List<String> list = RecordManager.getInstance().getOutputList();
		if (list != null && !list.isEmpty()) {
			for (String str : list) {
				System.out.println(str);
			}
		}
		
		System.out.println();
		System.out.println();
	}
}
