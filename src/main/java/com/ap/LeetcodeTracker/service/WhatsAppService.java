package com.ap.LeetcodeTracker.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ap.LeetcodeTracker.entity.TrackerEntity;
import com.ap.LeetcodeTracker.repository.ActivityRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(WhatsAppService.class);
	
    @Value("${twilio.whatsapp-from}")
    private String fromNumber;

    @Value("${twilio.whatsapp-to}")
    private String toNumber;

    @Autowired
    private ActivityRepository activityRepository;

    public void sendDailySummary(String username) {
    	LocalDate indiaDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        log.info("Preparing WhatsApp summary for username={} and indiaDate={}", username, indiaDate);
        Optional<TrackerEntity> optionalData =
                activityRepository.findByDateAndUsername(indiaDate, username);

        if (optionalData.isEmpty()) {
        	log.error("No activity found for username={} on date={}", username, indiaDate);
            throw new RuntimeException("No activity found for today for username: " + username);
        }

        TrackerEntity data = optionalData.get();

        String messageBody =
                "LeetCode Daily Update\n\n" +
                "Username: " + data.getUsername() + "\n" +
                "Date: " + data.getDate() + "\n" +
                "Time Spent: " + data.getLeetcodeTimeMinutes() + " minutes\n" +
                "Solved Today: " + data.getProblemsSolvedToday() + "\n" +
                "Total Solved: " + data.getTotalProblemsSolved() + "\n" +
                "Easy: " + data.getEasyCount() + "\n" +
                "Medium: " + data.getMediumCount() + "\n" +
                "Hard: " + data.getHardCount();
        log.info("Sending WhatsApp message. From={} To={}", fromNumber, toNumber);
        Message message = Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                messageBody
        ).create();
        log.info("WhatsApp message sent successfully. SID={}", message.getSid());
    }
}