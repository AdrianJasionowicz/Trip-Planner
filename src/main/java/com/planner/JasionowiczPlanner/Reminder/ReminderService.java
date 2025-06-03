package com.planner.JasionowiczPlanner.Reminder;

import com.planner.JasionowiczPlanner.Config.IcsGenerator;
import com.planner.JasionowiczPlanner.Config.MailService;
import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.LoginUser.LoginUserRepository;
import com.planner.JasionowiczPlanner.Mapper.PlaceMapper;
import com.planner.JasionowiczPlanner.Mapper.ReminderMapper;
import com.planner.JasionowiczPlanner.Mapper.TaskMapper;
import com.planner.JasionowiczPlanner.Mapper.TripMapper;
import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Place.PlaceRepository;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Task.TaskRepository;
import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripRepository;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReminderService {
private final ReminderRepository reminderRepository;
    private final PlaceRepository placeRepository;
    private final TaskRepository taskRepository;
    private final LoginUserRepository loginUserRepository;
    private ReminderMapper reminderMapper;
private TripMapper tripMapper;
private TaskMapper taskMapper;
private PlaceMapper placeMapper;
private TripRepository tripRepository;
    private final MailService mailService;
    private final IcsGenerator icsGenerator;

    public ReminderService(ReminderRepository reminderRepository, ReminderMapper reminderMapper, TripMapper tripMapper, TaskMapper taskMapper, PlaceMapper placeMapper, TripRepository tripRepository, PlaceRepository placeRepository, TaskRepository taskRepository, MailService mailService, IcsGenerator icsGenerator, LoginUserRepository loginUserRepository) {
        this.reminderRepository = reminderRepository;
        this.reminderMapper = reminderMapper;
        this.tripMapper = tripMapper;
        this.taskMapper = taskMapper;
        this.placeMapper = placeMapper;
        this.tripRepository = tripRepository;
        this.placeRepository = placeRepository;
        this.taskRepository = taskRepository;
        this.mailService = mailService;
        this.icsGenerator = icsGenerator;
        this.loginUserRepository = loginUserRepository;
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
        reminderDTO.setSend(false);
        Reminder reminder = reminderMapper.fromDto(reminderDTO);
        Trip trip = tripRepository.findById(reminderDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        reminder.setTrip(trip);

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
        if (reminderDTO.getTripId() != null) {
            reminder.setTrip(tripRepository.getReferenceById(reminderDTO.getTripId()));
        }

        if ( reminderDTO.getSend() != null) {
            reminder.setSend(reminderDTO.getSend());
        }
        if (reminderDTO.getMethod() != null) {
            reminder.setMethod(reminderDTO.getMethod());
        }
        if (reminderDTO.getPlaceId() != null) {
            reminder.setPlace(placeRepository.getReferenceById(reminderDTO.getPlaceId()));
        }
        if (reminderDTO.getTaskId() != null) {
            reminder.setTask(taskRepository.getReferenceById(reminderDTO.getTaskId()));
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

    public void sendReminderAsIcsEmail(Long id, Authentication authentication) {
        String user = authentication.getName();
        LoginUser loginUser = loginUserRepository.getByUsername(user);
        String email = loginUser.getEmail();

        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reminder not found: " + id));

        String icsContent = icsGenerator.generate(reminder);

        mailService.sendEmail(
                email,
                "📅 Przypomnienie: " + reminder.getTitle(),
                "W załączniku Twoje przypomnienie do kalendarza.",
                icsContent,
                "reminder.ics"
        );
    }
}