package com.planner.JasionowiczPlanner.Task;

import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.LoginUser.LoginUserRepository;
import com.planner.JasionowiczPlanner.Mapper.TaskMapper;
import com.planner.JasionowiczPlanner.Mapper.TripMapper;
import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripRepository;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TripRepository tripRepository;
    private final LoginUserRepository loginUserRepository;
    private TaskMapper taskMapper;
    private TripMapper tripMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, TripMapper tripMapper, TripRepository tripRepository, LoginUserRepository loginUserRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.tripMapper = tripMapper;
        this.tripRepository = tripRepository;
        this.loginUserRepository = loginUserRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllTaskByUserId(Long userId) {
        return taskRepository.findAllByTrip_LoginUserId(userId)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskByTaskId(Long id) {
        TaskDTO taskDTO = taskMapper.toDto(taskRepository.getReferenceById(id));
        return taskDTO;
    }

    public void createNewTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setDone(taskDTO.getDone());
        task.setDueDate(taskDTO.getDueDate());
        task.setCreatedAt(LocalDate.now());

        Trip trip = tripRepository.findById(taskDTO.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        task.setTrip(trip);

        taskRepository.save(task);
    }


    public void updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.getReferenceById(id);
        if (taskDTO.getName() != null) {
            task.setName(taskDTO.getName());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getDone() != null) {
            task.setDone(taskDTO.getDone());
        }
        if (taskDTO.getDueDate() != null) {
            task.setDueDate(taskDTO.getDueDate());
        }
        if (taskDTO.getTripId() != null) {
            task.setTrip(tripRepository.getReferenceById(taskDTO.getTripId()));
        }
        if (taskDTO.getCreatedAt() != null) {
            task.setCreatedAt(taskDTO.getCreatedAt());
        }
        if (taskDTO.getChecklist() != null) {
            task.setChecklist(taskDTO.getChecklist());
        }
        taskRepository.save(task);

    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void changeTaskStatus(Long id) {
        Task task = taskRepository.getReferenceById(id);
        task.setDone(true);
        taskRepository.save(task);
    }

    public List<TaskDTO> getTasksByTripId(Long tripId) {
        List<Task> tasks = taskRepository.findByTrip_Id(tripId);
        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getAllTaskByUserAuthentication(Authentication authentication) {
        String username = authentication.getName();
        LoginUser loginUser = loginUserRepository.getByUsername(username);
        List<Task> trips =  taskRepository.findAllByTrip_LoginUser_Id(loginUser.getId());
        return trips
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());

    }
}
