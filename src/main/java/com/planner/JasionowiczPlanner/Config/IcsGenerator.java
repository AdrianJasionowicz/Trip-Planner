package com.planner.JasionowiczPlanner.Config;

import com.planner.JasionowiczPlanner.Reminder.Reminder;
import org.springframework.stereotype.Component;

@Component
public class IcsGenerator {
    public String generate(Reminder rem) {
        String date = rem.getCreatedAt().toString().replaceAll("-", "") + "T090000Z";
        String end = rem.getRemindAt().toString().replaceAll("-", "") + "T100000Z";

        return "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "BEGIN:VEVENT\n" +
                "SUMMARY:" + rem.getTitle() + "\n" +
                "DTSTART:" + date + "\n" +
                "DTEND:" + end + "\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
    }
}
