package com.planner.JasionowiczPlanner.Trip;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<TripDTO>> getTrips(@PathVariable Long userId) {
        List<TripDTO> tripsDto = tripService.getUserTrips(userId);

       return tripsDto.isEmpty()
               ? ResponseEntity.noContent().header("No Trips Found").build()
               : ResponseEntity.ok().header("Trips Found").body(tripsDto);
    }

    @PostMapping("/trips")
    public ResponseEntity<String> createTrip(@RequestBody TripDTO trip) {
        tripService.addTrip(trip);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Trip created succesfully")
                .build();
    }

    @PutMapping("/trips/{tripId}")
    public ResponseEntity<String> updateTrip(@PathVariable Long tripId, @RequestBody TripDTO trip) {
        tripService.updateTrip(tripId,trip);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Trip updated succesfully")
                .build();
    }
    @DeleteMapping("/trips/{tripId}")
    public ResponseEntity deleteTrip(@PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Trip deleted succesfully")
                .build();
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<TripDTO> getTrip(@PathVariable Long tripId) {
        TripDTO tripDTO = tripService.getTrip(tripId);
        return tripDTO == null
                ? ResponseEntity.noContent()
                .header("No Trip Found")
                .build()
                : ResponseEntity.ok()
                .header("Trip Found")
                .body(tripDTO);
    }

}
