package com.masai.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.Entity.Task;
import com.masai.Exception.TaskException;
import com.masai.Service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
        } catch (TaskException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
        } catch (TaskException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {
            taskService.deleteTaskById(id);
            return new ResponseEntity<String>("Task deleted successfully", HttpStatus.OK);
        } catch (TaskException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable int userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable int id) {
        try {
            Task task = taskService.getTaskById(id);
            return new ResponseEntity<Task>(task, HttpStatus.OK);
        } catch (TaskException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/complete/{taskId}")
    public ResponseEntity<String> markTaskAsCompleted(@PathVariable int taskId) {
        try {
            Task task = taskService.getTaskById(taskId);

            if ("pending".equalsIgnoreCase(task.getStatus())) {
                task.setStatus("completed");
                Task completedTask = taskService.updateTask(taskId, task);
                return new ResponseEntity<String>("Task marked as completed", HttpStatus.OK);
            } else {
            	return new ResponseEntity<String>("Task is already marked as completed", HttpStatus.BAD_REQUEST);
            }
        } catch (TaskException e) {
        	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/overdue/{taskId}")
    public ResponseEntity<String> markTaskAsPending(@PathVariable int taskId) {
        try {
            Task task = taskService.getTaskById(taskId);

            if ("completed".equalsIgnoreCase(task.getStatus())) {
                task.setStatus("overdue");
                Task overdueTask = taskService.updateTask(taskId, task);
                return new ResponseEntity<String>("Task marked as overdue", HttpStatus.OK);
            } else {
            	return new ResponseEntity<String>("Task is already marked as overdue", HttpStatus.BAD_REQUEST);
            }
        } catch (TaskException e) {
        	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

