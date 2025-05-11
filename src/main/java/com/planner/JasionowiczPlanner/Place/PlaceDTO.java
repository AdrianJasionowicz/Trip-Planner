package com.planner.JasionowiczPlanner.Place;


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
public class PlaceDTO {

    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String googleMapsUrl;
    private Trip trip;
    private LocalDateTime createdAt;
    private String imageUrl;
    private LocalDateTime visitDateTimeFrom;
    private LocalDateTime visitDateTimeTo;
    private String notes;
    private List<String> tags;
}
