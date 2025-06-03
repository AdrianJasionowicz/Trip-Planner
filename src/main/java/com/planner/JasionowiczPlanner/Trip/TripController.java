package com.planner.JasionowiczPlanner.Trip;

import com.planner.JasionowiczPlanner.Reminder.ReminderDTO;
import com.planner.JasionowiczPlanner.Task.TaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripController {
private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips")
    public ResponseEntity<List<TripDTO>> getTrips(Authentication authentication) {

        List<TripDTO> tripsDto = tripService.getTripsByAuthentication(authentication);
        return tripsDto.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tripsDto);
    }

    @PostMapping("/trips")
    public ResponseEntity<String> createTrip(@RequestBody TripDTO trip,Authentication authentication) {
        tripService.addTrip(trip,authentication);
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


    @GetMapping("/trip/{tripId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable Long tripId, Authentication authentication) {
       List<TaskDTO> taskDTOList = tripService.getTasksByTripId(tripId);
        if (taskDTOList.isEmpty()) {
            return ResponseEntity.noContent()
                    .header("X-Info", "No Tasks Found")
                    .build();
        }
        return ResponseEntity.ok()
                .header("X-Info", "Tasks Found")
                .body(taskDTOList);
    }

    @GetMapping("/trip/{tripId}/reminders")
    public ResponseEntity<List<ReminderDTO>> getReminders(@PathVariable Long tripId, Authentication authentication) {
        List<ReminderDTO> reminderDTOList = tripService.getRemindersByTripId(tripId);
        if (reminderDTOList.isEmpty()) {
            return ResponseEntity.noContent()
                    .header("X-Info", "No Reminders Found")
                    .build();
        }
        return ResponseEntity.ok()
                .header("X-Info", "Reminders Found")
                .body(reminderDTOList);
    }

}
