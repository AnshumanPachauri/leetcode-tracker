package com.ap.LeetcodeTracker.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ap.LeetcodeTracker.dto.LeetcodeExtractedStats;
import com.ap.LeetcodeTracker.dto.LeetcodeResponse;
import com.ap.LeetcodeTracker.entity.TrackerEntity;
import com.ap.LeetcodeTracker.repository.ActivityRepository;

@Service
public class ActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private LeetCodeService leetCodeService;

	public TrackerEntity updateActivity(TrackerEntity trackerEntity) {
		
		
		
//		Optional<TrackerEntity> existingActivity = activityRepository.findByDate(trackerEntity.getDate());
		Optional<TrackerEntity> existingActivity = activityRepository.findByDateAndUsername(trackerEntity.getDate(), trackerEntity.getUsername());
		
		if(existingActivity.isPresent()) {
			TrackerEntity entity = existingActivity.get();
			
//			entity.setLeetcodeTimeMinutes(trackerEntity.getLeetcodeTimeMinutes());
			entity.setProblemsSolvedToday(trackerEntity.getProblemsSolvedToday());
			entity.setTotalProblemsSolved(trackerEntity.getTotalProblemsSolved());
			entity.setEasyCount(trackerEntity.getEasyCount());
			entity.setMediumCount(trackerEntity.getMediumCount());
			entity.setHardCount(trackerEntity.getHardCount());
			
			return activityRepository.save(entity);
		}
		return activityRepository.save(trackerEntity);
	}
	
	
	
	public TrackerEntity saveDailyUpdate(String username) {
		
		//fetching current data from leetcode graphQL query.
		LeetcodeResponse response = leetCodeService.fetchUserStats(username);
		LeetcodeExtractedStats currentStats = leetCodeService.extractStats(response);
		
		int totalSolved = currentStats.getTotalSolved();
		
		//fetching last day data from my daily_activity table
		
		Optional<TrackerEntity> lastDayRecord = activityRepository.findTopByUsernameOrderByDateDesc(username);
		
		int lastDayTotal = 0;
		
		if(lastDayRecord.isPresent()) {
			lastDayTotal = lastDayRecord.get().getTotalProblemsSolved();
		}
		
		//todays solved questions
		
		int solvedToday = totalSolved - lastDayTotal;
		
		solvedToday = Math.max(solvedToday, 0);
		
		//create new day activity (today's activity)
		
		TrackerEntity todaysActivity = new TrackerEntity();
		todaysActivity.setUsername(username);
		todaysActivity.setDate(LocalDate.now());
		todaysActivity.setProblemsSolvedToday(solvedToday);
		todaysActivity.setTotalProblemsSolved(totalSolved);
		
		//Setting questions as per difficulty level
		
		todaysActivity.setEasyCount(currentStats.getEasySolved());
		todaysActivity.setMediumCount(currentStats.getMediumSolved());
		todaysActivity.setHardCount(currentStats.getHardSolved());
		
		
		//timeSpentOnLeetcode will make later.
		
		todaysActivity.setLeetcodeTimeMinutes(0);
		
		return updateActivity(todaysActivity);
	}



	public void addTimeAndUsername(int minutes, String username) {
		// TODO Auto-generated method stub
		
		LocalDate today = LocalDate.now();
		
		Optional<TrackerEntity> existing = activityRepository.findByDateAndUsername(today, username);
		
		if(existing.isPresent()) {
			TrackerEntity existingEntity = existing.get();
			
			existingEntity.setLeetcodeTimeMinutes(
					existingEntity.getLeetcodeTimeMinutes() + minutes
					);
			
			activityRepository.save(existingEntity);
		}
		else{
			TrackerEntity newEntity = new TrackerEntity();
			
			newEntity.setUsername(username);
			newEntity.setDate(today);
			newEntity.setLeetcodeTimeMinutes(minutes);
			activityRepository.save(newEntity);
		}

	}
	
}
