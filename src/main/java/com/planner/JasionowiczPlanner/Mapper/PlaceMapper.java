package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Place.PlaceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    PlaceDTO toDto(Place place);
    Place toEntity(PlaceDTO placeDTO);
}
