package com.planner.JasionowiczPlanner.Trip;

import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.LoginUser.LoginUserRepository;
import com.planner.JasionowiczPlanner.Mapper.PlaceMapper;
import com.planner.JasionowiczPlanner.Mapper.ReminderMapper;
import com.planner.JasionowiczPlanner.Mapper.TaskMapper;
import com.planner.JasionowiczPlanner.Mapper.TripMapper;
import com.planner.JasionowiczPlanner.Place.Place;
import com.planner.JasionowiczPlanner.Reminder.Reminder;
import com.planner.JasionowiczPlanner.Reminder.ReminderDTO;
import com.planner.JasionowiczPlanner.Task.Task;
import com.planner.JasionowiczPlanner.Task.TaskDTO;
import com.planner.JasionowiczPlanner.Task.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final TaskRepository taskRepository;
    private LoginUserRepository loginUserRepository;
    private PlaceMapper placeMapper;
    private ReminderMapper reminderMapper;
    private TaskMapper taskMapper;


    public TripService(TripRepository tripRepository, TripMapper tripMapper, LoginUserRepository loginUserRepository, PlaceMapper placeMapper, ReminderMapper reminderMapper, TaskMapper taskMapper, TaskRepository taskRepository) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
        this.loginUserRepository = loginUserRepository;
        this.placeMapper = placeMapper;
        this.reminderMapper = reminderMapper;
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    public List<TripDTO> getUserTrips(Long userId) {
        List<Trip> trips = tripRepository.findByLoginUser_Id(userId);
        List<TripDTO> dtos = new ArrayList<>();
        for (Trip trip : trips) {
            dtos.add(tripMapper.toDto(trip));
        }
        return dtos;

    }

    public void addTrip(TripDTO tripDTO, Authentication authentication) {
        String username = authentication.getName();
        LoginUser user = loginUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));

        Trip trip = tripMapper.toTrip(tripDTO);
        trip.setLoginUser(user);

        tripRepository.save(trip);
    }

    public void updateTrip(Long tripId, TripDTO trip) {
        Trip trip1 = tripRepository.getReferenceById(tripId);

        if (trip.getCity() != null) trip1.setCity(trip.getCity());
        if (trip.getDescription() != null) trip1.setDescription(trip.getDescription());
        if (trip.getTitle() != null) trip1.setTitle(trip.getTitle());
        if (trip.getStartDate() != null) trip1.setStartDate(trip.getStartDate());
        if (trip.getEndDate() != null) trip1.setEndDate(trip.getEndDate());
        if (trip.getStatus() != null) trip1.setStatus(trip.getStatus());
        if (trip.getCreatedAt() != null) trip1.setCreatedAt(trip.getCreatedAt());
        if (trip.getImageCoverUrl() != null) trip1.setImageCoverUrl(trip.getImageCoverUrl());
        if (trip.getCountry() != null) trip1.setCountry(trip.getCountry());

        if (trip.getPlaces() != null) {
            List<Place> places = placeMapper.toEntity(trip.getPlaces());
            places.forEach(p -> p.setTrip(trip1));
            trip1.setPlaces(places);
        }

        if (trip.getReminders() != null) {
            List<Reminder> reminders = trip.getReminders().stream()
                    .map(reminderMapper::fromDto)
                    .peek(r -> r.setTrip(trip1))
                    .toList();
            trip1.setReminders(reminders);
        }

        if (trip.getTasks() != null) {
            List<Task> tasks = trip.getTasks().stream()
                    .map(taskMapper::toTask)
                    .peek(t -> t.setTrip(trip1))
                    .toList();
            trip1.setTasks(tasks);
        }

        tripRepository.save(trip1);
    }

    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    public TripDTO getTrip(Long tripId) {
        Trip trip = tripRepository.getById(tripId);
        TripDTO tripDTO = new TripDTO();
        tripDTO = tripMapper.toDto(trip);
        return tripDTO;
    }

    public List<TripDTO> getTripsByAuthentication(Authentication authentication) {
        String username = authentication.getName();
        LoginUser user = loginUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));

        return user.getTrips().stream()
                .map(tripMapper::toDto)
                .toList();
    }


    public List<TaskDTO> getTasksByTripId(Long tripId) {
        Trip trip = tripRepository.getReferenceById(tripId);
        List<Task> tasks = trip.getTasks();
        return tasks
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public List<ReminderDTO> getRemindersByTripId(Long tripId) {
        Trip trip = tripRepository.getReferenceById(tripId);
        List<Reminder> reminders  = trip.getReminders();
        return reminders
                .stream()
                .map(reminderMapper::toDto)
                .toList();
    }
}
