package com.ap.LeetcodeTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ap.LeetcodeTracker.entity.TrackerEntity;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface ActivityRepository extends JpaRepository<TrackerEntity, Long>{
	
	Optional<TrackerEntity> findByDate(LocalDate date);
	Optional<TrackerEntity> findTopByOrderByDateDesc();
	Optional<TrackerEntity> findByDateAndUsername(LocalDate date, String username);
	Optional<TrackerEntity> findTopByUsernameOrderByDateDesc(String username);
}
