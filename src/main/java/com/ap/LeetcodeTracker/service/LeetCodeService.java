package com.ap.LeetcodeTracker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ap.LeetcodeTracker.dto.AcSubmissionNum;
import com.ap.LeetcodeTracker.dto.LeetcodeExtractedStats;
import com.ap.LeetcodeTracker.dto.LeetcodeResponse;

@Service
public class LeetCodeService {
	
	private final RestTemplate restTemplate = new RestTemplate();

	public LeetcodeResponse fetchUserStats(String username) {
		
		String url = "https://leetcode.com/graphql";
		
		String query = """
				{
				matchedUser(username: "%s"){
					 submitStats{
						acSubmissionNum{
								difficulty
								count
							}
						}
					}
				}
				""".formatted(username);
		
		Map<String, String> request = new HashMap<String, String>();
		request.put("query", query);
		
		ResponseEntity<LeetcodeResponse> response = restTemplate.postForEntity(url, request, LeetcodeResponse.class);
		
		return response.getBody();
	}
	
	public LeetcodeExtractedStats extractStats(LeetcodeResponse response) {
		
		List<AcSubmissionNum> submissions = response.getData().getMatchedUser().getSubmitStats().getAcSubmissionNum();
		
		LeetcodeExtractedStats extractedStats = new LeetcodeExtractedStats();
		
		for (AcSubmissionNum i : submissions) {
			
			switch(i.getDifficulty()) {
			case "All":
				extractedStats.setTotalSolved(i.getCount());
				break;
				
			case "Easy":
				extractedStats.setEasySolved(i.getCount());
				break;
			
			case "Medium":
				extractedStats.setMediumSolved(i.getCount());
				break;
				
			case "Hard":
				extractedStats.setHardSolved(i.getCount());
				break;
			
			}
			
		}
		
		return extractedStats;
		
	} 
	
}
