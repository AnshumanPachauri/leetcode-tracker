
package com.ap.LeetcodeTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.LeetcodeTracker.dto.LeetcodeExtractedStats;
import com.ap.LeetcodeTracker.dto.LeetcodeResponse;
import com.ap.LeetcodeTracker.entity.TrackerEntity;
import com.ap.LeetcodeTracker.service.ActivityService;
import com.ap.LeetcodeTracker.service.LeetCodeService;

@RestController
@RequestMapping("/leetcode")
public class leetcodeController {

	@Autowired
	private LeetCodeService leetCodeService;
	@Autowired
	private ActivityService activityService;
	
//	@GetMapping("/{username}")
//	public LeetcodeExtractedStats getStats(@PathVariable String username) {
//		LeetcodeResponse response = leetCodeService.fetchUserStats(username);
//		return leetCodeService.extractStats(response);
//		
//	}
	
	@GetMapping("/{username}")
	public TrackerEntity getStats(@PathVariable String username) {
		return activityService.saveDailyUpdate(username);
//		return leetCodeService.extractStats(response);
		
	}
	
}
