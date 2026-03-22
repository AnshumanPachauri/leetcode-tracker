package com.ap.LeetcodeTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.LeetcodeTracker.service.WhatsAppService;

@RestController
@RequestMapping("/twilio/test")
public class TwilioTestController {

	@Autowired
	private WhatsAppService whatsAppService;
	
	@GetMapping("/whatsapp/{username}")
	public String testWhatsapp(@PathVariable String username) {
		
		whatsAppService.sendDailySummary(username);
		return "WhatsApp message sent successfully";
		
	}
	
}
