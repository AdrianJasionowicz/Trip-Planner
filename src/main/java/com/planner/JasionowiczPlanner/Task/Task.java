package com.planner.JasionowiczPlanner.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String description;
    private Boolean done;
    @JsonFormat(pattern = "yyyy-MM-dd")

    private LocalDate dueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    private LocalDate createdAt;
    @ElementCollection
    private List<String> checklist;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }
}
