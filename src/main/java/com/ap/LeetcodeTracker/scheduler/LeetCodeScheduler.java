package com.ap.LeetcodeTracker.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.ap.LeetcodeTracker.repository.ActivityRepository;
import com.ap.LeetcodeTracker.service.ActivityService;
import com.ap.LeetcodeTracker.service.WhatsAppService;

@Component
public class LeetCodeScheduler {

	@Autowired
	private ActivityService activityService;
	
	@Autowired
    private WhatsAppService whatsAppService;
	
	private final String username = "Anshuman_2002";
	
	private static final Logger log =
            LoggerFactory.getLogger(LeetCodeScheduler.class);
	
//	@Scheduled(cron = "0 59 23 * * ?")
	@Scheduled(cron = "0 0 * * * ?")
	public void scheduleDailyLeetcodeprogress() {
		
		System.out.println("Scheduler is Running daily LeetCode tracker...");
		
		log.info("Scheduler is Running daily LeetCode tracker...");
		
//		activityService.saveDailyUpdate(username);
		
		try {
			activityService.saveDailyUpdate(username);
			whatsAppService.sendDailySummary(username);
			log.info("Scheduler Job is done...");
	    }
	    catch(Exception e){
	        log.error("Daily tracker job failed", e);
	    }
		
		
	}
	
}
