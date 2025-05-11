package com.planner.JasionowiczPlanner.Reminder;

import com.planner.JasionowiczPlanner.Mapper.ReminderMapper;
import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {
private final ReminderRepository reminderRepository;
private ReminderMapper reminderMapper;

    public ReminderService(ReminderRepository reminderRepository, ReminderMapper reminderMapper) {
        this.reminderRepository = reminderRepository;
        this.reminderMapper = reminderMapper;
    }

    public List<ReminderDTO> getAllReminders() {
        List<Reminder> reminders = reminderRepository.findAll();
        return reminders
                .stream()
                .map(reminderMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReminderDTO getReminderById(long id) {
        ReminderDTO reminderDTO = reminderMapper.toDto(reminderRepository.getReferenceById(id));
        return reminderDTO;
    }

    public void createNewReminder(ReminderDTO reminderDTO) {
        Reminder reminder = new Reminder();
        reminder = reminderMapper.fromDto(reminderDTO);
        reminderRepository.save(reminder);
    }

    public void updateReminder(long id, ReminderDTO reminderDTO) {
        Reminder reminder = reminderRepository.getReferenceById(id);
        if (reminderDTO.getTitle() != null) {
            reminder.setTitle(reminderDTO.getTitle());
        }
        if (reminderDTO.getDescription() != null) {
            reminder.setDescription(reminderDTO.getDescription());
        }
        if (reminderDTO.getRemindAt() != null) {
            reminder.setRemindAt(reminderDTO.getRemindAt());
        }
        if ( reminderDTO.getCreatedAt() != null) {
            reminder.setCreatedAt(reminderDTO.getCreatedAt());
        }
        if ( reminderDTO.getTrip() != null) {
            reminder.setTrip(reminderDTO.getTrip());
        }
        if ( reminderDTO.getSend() != null) {
            reminder.setSend(reminderDTO.getSend());
        }
        if (reminderDTO.getMethod() != null) {
            reminder.setMethod(reminderDTO.getMethod());
        }
        if (reminderDTO.getPlace() != null) {
            reminder.setPlace(reminderDTO.getPlace());
        }
        if (reminderDTO.getTask() != null) {
            reminder.setTask(reminderDTO.getTask());
        }
        reminderRepository.save(reminder);
    }

    public void deleteReminderById(long id) {
        reminderRepository.deleteById(id);
    }

    public List<ReminderDTO> getAllRemindersByTripId(long tripId) {
        return reminderRepository.findByTrip_Id(tripId)
                .stream()
                .map(reminderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReminderDTO> getAllRemindersByUserId(long userId) {
    List<Reminder> reminderList = reminderRepository.findByTrip_LoginUser_Id(userId);
        return reminderList
                .stream()
                .map(reminderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReminderDTO> getNearRemindersForUser(long userId) {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusDays(2).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        List<Reminder> reminders = reminderRepository.findByTrip_LoginUser_IdAndRemindAtBetween(userId, start, end);

        return reminders.stream()
                .map(reminderMapper::toDto)
                .collect(Collectors.toList());
    }

}