package com.ap.LeetcodeTracker.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ap.LeetcodeTracker.entity.TrackerEntity;
import com.ap.LeetcodeTracker.repository.ActivityRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {

    @Value("${twilio.whatsapp-from}")
    private String fromNumber;

    @Value("${twilio.whatsapp-to}")
    private String toNumber;

    @Autowired
    private ActivityRepository activityRepository;

    public void sendDailySummary(String username) {
        Optional<TrackerEntity> optionalData =
                activityRepository.findByDateAndUsername(LocalDate.now(), username);

        if (optionalData.isEmpty()) {
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

        Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                messageBody
        ).create();
    }
}