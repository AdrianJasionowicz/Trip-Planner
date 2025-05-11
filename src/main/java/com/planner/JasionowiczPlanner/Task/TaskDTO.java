package com.planner.JasionowiczPlanner.Task;

import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {


    private Long id;
    private String name;
    private String description;
    private Boolean done;

    private LocalDateTime dueDate;
    private Trip trip;
    private LocalDateTime createdAt;
    private List<String> checklist;

}



