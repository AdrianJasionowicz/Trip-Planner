package com.planner.JasionowiczPlanner.Reminder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByTrip_Id(long tripId);
    List<Reminder> findByTrip_LoginUser_Id(Long userId);
    List<Reminder> findByTrip_LoginUser_IdAndRemindAtBetween(Long userId, LocalDateTime start, LocalDateTime end);



}
