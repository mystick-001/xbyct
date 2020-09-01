/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *      一米七                                       1.0.0        2020年9月1日 上午10:31:47
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct.thread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.xbyct.core.RecordManager;

/**
 * 处理以文件形式输入的支付记录
 * 
 * @author liu-guoping
 *
 */
public class RecordFileLoadThread extends Thread {

	/**
	 * the file of record of payment
	 */
	private String url;

	public RecordFileLoadThread(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		System.out.println("Load record file starting ...");
		
		List<String> list = null;
		try {
			// read file
			list = Files.readAllLines(Paths.get(url));
			if (list == null || list.isEmpty()) {
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String str : list) {
			if (str == null || "".equals(str.trim())) {
				continue;
			}
			// explain a command line
			RecordManager.getInstance().explainLine(str.trim());
		}

		System.out.println("Load record file finished!");
	}
}
