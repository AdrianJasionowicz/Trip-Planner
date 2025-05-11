package com.planner.JasionowiczPlanner.Place;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
}
