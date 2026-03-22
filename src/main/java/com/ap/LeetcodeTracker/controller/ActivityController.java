
package com.ap.LeetcodeTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.LeetcodeTracker.entity.TrackerEntity;
import com.ap.LeetcodeTracker.service.ActivityService;

@RestController
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	@PostMapping("/update")
	public TrackerEntity update(@RequestBody TrackerEntity trackerEntity) {
		
		return activityService.updateActivity(trackerEntity);
		
	}
	
}
