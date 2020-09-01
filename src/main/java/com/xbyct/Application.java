/**
 * Enrich your life everyday, code is mystery.
 *-------------------------------------------------------------
 *      Author        Version         Time
 *-------------------------------------------------------------
 *      一米七                                       1.0.0        2020年9月1日 上午8:02:24
 *-------------------------------------------------------------
 * Copyright © 2015–2020 All rights reserved.
 */
package com.xbyct;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.xbyct.core.RecordManager;
import com.xbyct.thread.RecordFileLoadThread;

/**
 * starting class
 * 
 * @author liu-guoping
 *
 */
@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// load the init file if exist
		for (int k = 0; k < args.length - 1;) {
			if (("-f".equals(args[k]) || "-F".equals(args[k])) && !"".equals(args[k + 1])) {
				// start the record file init thread
				new RecordFileLoadThread(args[k + 1]).start();
				k += 2;
			} else {
				k++;
			}
		}

		// accept console command
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while (true) {
			line = reader.readLine();
			if (line == null || "".equals(line)) {
				continue;
			}
			line = line.trim();
			if ("quit".equals(line.toLowerCase())) {
				System.out.println("System quit !");
				break;
			} else {
				RecordManager.getInstance().explainLine(line);
			}
		}
		
		System.exit(0);

	}

}
