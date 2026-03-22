package com.ap.LeetcodeTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//Enabling the scheduler for automatic leetcode sevice call without manually calling the API.

@SpringBootApplication
@EnableScheduling
public class LeetcodeTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeetcodeTrackerApplication.class, args);
	}

}
