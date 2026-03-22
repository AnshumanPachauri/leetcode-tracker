package com.ap.LeetcodeTracker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

	@Value("${twilio.account-sid}")
	private String accountSid;
	
	@Value("${twilio.auth-token}")
	private String authToken;
	
    @PostConstruct
    public void initTwilio() {
		System.out.println("TWILIO_ACCOUNT_SID = " + accountSid);
        System.out.println("TWILIO_AUTH_TOKEN present = " + !("NOT_FOUND".equals(authToken)));
        Twilio.init(accountSid, authToken);
    }
	
}
