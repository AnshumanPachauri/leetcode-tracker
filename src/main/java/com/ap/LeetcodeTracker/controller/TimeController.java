package com.ap.LeetcodeTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.LeetcodeTracker.dto.TimeRequest;
import com.ap.LeetcodeTracker.service.ActivityService;

@RestController
@RequestMapping("/api")
public class TimeController {

	@Autowired
	private ActivityService activityService;
	@PostMapping("/time")
	public void RecieveTime(@RequestBody TimeRequest timeRequest) {
		
		System.out.println("Received from extension: "
                + timeRequest.getUsername() + " - "
                + timeRequest.getMinutes());
		
		activityService.addTimeAndUsername(
			timeRequest.getMinutes(),
			timeRequest.getUsername()
				);
	}
	
}
