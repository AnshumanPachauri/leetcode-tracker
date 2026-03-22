package com.ap.LeetcodeTracker.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
		name="daily_activity",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"username", "date"})
			}
		)
@Data
public class TrackerEntity {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private LocalDate date;

    private int leetcodeTimeMinutes;

    private int problemsSolvedToday;

    private int easyCount;
    
    private int mediumCount;
    
    private int hardCount;
	
    private int totalProblemsSolved;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private String username;
}
