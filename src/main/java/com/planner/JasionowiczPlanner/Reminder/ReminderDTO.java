package com.planner.JasionowiczPlanner.Reminder;

import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReminderDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime remindAt;
    private LocalDateTime createdAt;
    private Trip trip;
    private String method;
    private Boolean send;
    private Place place;
    private Task task;


}