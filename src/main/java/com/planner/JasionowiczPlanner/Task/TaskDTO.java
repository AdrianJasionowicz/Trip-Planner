package com.planner.JasionowiczPlanner.Task;

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
public class TaskDTO {


    private Long id;
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private Trip trip;
    private LocalDateTime createdAt;

}
