package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripMapper {
    @Mapping(target = "loginUser.trips", ignore = true)
    @Mapping(target = "places", ignore = true)
    TripDTO toDto(Trip trip);
    Trip toTrip(TripDTO tripDTO);
}
