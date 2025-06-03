package com.planner.JasionowiczPlanner.Task;

import com.planner.JasionowiczPlanner.Mapper.TaskMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> taskDTOList = taskService.getAllTasks();
        return taskDTOList.isEmpty()
                ? ResponseEntity.noContent().header("Error", "No tasks found").build()
                : ResponseEntity.ok(taskDTOList);
    }

    @GetMapping("/tasks/{userId}")
    public ResponseEntity<List<TaskDTO>> getTask(@PathVariable Long userId) {
        List<TaskDTO> taskDTOList = taskService.getAllTaskByUserId(userId);
        return taskDTOList.isEmpty()
                ? ResponseEntity.noContent().header("Error","No tasks found").build()
                : ResponseEntity.ok(taskDTOList);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskByTaskId(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Task has been found")
                .body(taskDTO);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
       taskService.createNewTask(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDTO);
    }


    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        taskService.updateTask(id,taskDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Task update succesfully")
                .build();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Task deleted succesfully")
                .build();
    }

    @PatchMapping("/tasks/{id}/complete")
    public ResponseEntity<TaskDTO> changeIsDone(@RequestBody Long id) {
        taskService.changeTaskStatus(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Task changed to done succesfully")
                .build();
    }

    @GetMapping("/tasks/trip/{tripId}")
    public ResponseEntity<List<TaskDTO>> getTasksByTripId(@PathVariable Long tripId) {
        List<TaskDTO> tasks = taskService.getTasksByTripId(tripId);
        return tasks.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tasks);
    }


    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(Authentication authentication ) {
        List<TaskDTO> taskDTOS = taskService.getAllTaskByUserAuthentication(authentication);
        return taskDTOS.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(taskDTOS);
    }

}






