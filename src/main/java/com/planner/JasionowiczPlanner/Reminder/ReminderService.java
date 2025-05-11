package com.planner.JasionowiczPlanner.Reminder;

import org.springframework.stereotype.Service;

@Service
public class ReminderService {
private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }
}
