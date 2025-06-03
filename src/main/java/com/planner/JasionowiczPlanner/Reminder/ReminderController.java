package com.planner.JasionowiczPlanner.Reminder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReminderController {
    private ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping("/reminders")
    public ResponseEntity<List<ReminderDTO>> getAllReminders() {
        List<ReminderDTO> reminderDTO = reminderService.getAllReminders();
        return reminderDTO.isEmpty()
                ? ResponseEntity.noContent().header("Error no reminders found").build()
                : ResponseEntity.ok(reminderDTO);
    }
    @GetMapping("/reminders/{id}")
    public ResponseEntity<ReminderDTO> getReminderById(@PathVariable long id) {
        ReminderDTO reminderDTO = reminderService.getReminderById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Reminder has been found")
                .body(reminderDTO);
    }

    @PostMapping("/reminders")
    public ResponseEntity<ReminderDTO> createReminder(@RequestBody ReminderDTO reminderDTO) {
        reminderService.createNewReminder(reminderDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Reminder has been created")
                .body(reminderDTO);
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<ReminderDTO> updateReminder(@PathVariable long id, @RequestBody ReminderDTO reminderDTO) {
        reminderService.updateReminder(id,reminderDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Reminder has been updated")
                .body(reminderDTO);
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<String> deleteReminder(@PathVariable long id) {
        reminderService.deleteReminderById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Reminder deleted")
                .body("Reminder deleted");
    }

    @GetMapping("/reminders/trip/{tripId}")
    public ResponseEntity<List<ReminderDTO>> getAllRemindersByTripId(@PathVariable long tripId) {
      List<ReminderDTO> reminderDTOList = reminderService.getAllRemindersByTripId(tripId);
        return reminderDTOList.isEmpty()
                ? ResponseEntity.noContent().header("Reminders not found").build()
                : ResponseEntity.ok(reminderDTOList);
    }

    @GetMapping("/reminders/user/{userId}")
    public ResponseEntity<List<ReminderDTO>> getAllRemindersByUserId(@PathVariable long userId) {
        List<ReminderDTO> reminderDTOList = reminderService.getAllRemindersByUserId(userId);
        return reminderDTOList.isEmpty()
                ? ResponseEntity.noContent().header("Reminders not found").build()
                : ResponseEntity.ok(reminderDTOList);
    }

//    @GetMapping("/reminders/near")
//    public List<ReminderDTO> getNearRemindersForUser(@AuthenticationPrincipal User user) {
//        return reminderService.getNearRemindersForUser(user.getId());
//    }


    @PostMapping("/reminders/{id}/send-ics")
    public ResponseEntity<Void> sendReminderIcs(@PathVariable Long id, Authentication authentication) {

        reminderService.sendReminderAsIcsEmail(id,authentication);
        return ResponseEntity.ok().build();
    }




}
