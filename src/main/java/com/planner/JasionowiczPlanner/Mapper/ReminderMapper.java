package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Reminder.Reminder;
import com.planner.JasionowiczPlanner.Reminder.ReminderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReminderMapper {
    ReminderDTO toDto(Reminder reminder);
    Reminder fromDto(ReminderDTO reminderDTO);
}
