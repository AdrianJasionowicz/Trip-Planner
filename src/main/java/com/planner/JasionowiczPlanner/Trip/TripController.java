package com.planner.JasionowiczPlanner.Trip;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripController {
private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips/{userId}")
    public List<TripDTO> getTrips(@PathVariable Long userId) {
       return tripService.getUserTrips(userId);
    }

    @PostMapping("/trips")
    public void createTrip(@RequestBody TripDTO trip) {
        tripService.addTrip(trip);
    }

    @PutMapping("/trips/{tripId}")
    public void updateTrip(@PathVariable Long tripId, @RequestBody TripDTO trip) {
        tripService.updateTrip(tripId,trip);

    }
    @DeleteMapping("/trips/{tripId}")
    public void deleteTrip(@PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
    }

    @GetMapping("/trip/{tripId}")
    public TripDTO getTrip(@PathVariable Long tripId) {
        return tripService.getTrip(tripId);
    }

}

//DELETE /trips/{tripId} – usuwanie

