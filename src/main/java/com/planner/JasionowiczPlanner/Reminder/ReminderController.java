package com.planner.JasionowiczPlanner.Reminder;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderController {
private ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }
}
