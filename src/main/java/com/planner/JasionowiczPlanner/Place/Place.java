package com.planner.JasionowiczPlanner.Place;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String googleMapsUrl;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private String imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDateTimeFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate visitDateTimeTo;

    private String notes;
    @ElementCollection
    private List<String> tags;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }


    public Duration timeUntilTrip() {
        return Duration.between(LocalDateTime.now(), this.visitDateTimeFrom);
    }
}
