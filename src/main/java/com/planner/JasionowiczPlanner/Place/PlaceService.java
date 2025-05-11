package com.planner.JasionowiczPlanner.Place;

import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }
}
