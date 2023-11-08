package com.masai.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.createTask(task);
            return ResponseEntity.ok(createdTask);
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body("Error while creating Task.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body("Error while updating Task."); 
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {
            taskService.deleteTaskById(id);
            return ResponseEntity.noContent().build();
        } catch (TaskException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable int userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (TaskException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/complete/{taskId}")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable int taskId) {
        try {
            Task task = taskService.getTaskById(taskId);

            if ("pending".equalsIgnoreCase(task.getStatus())) {
                task.setStatus("completed");
                Task completedTask = taskService.updateTask(taskId, task);
                return ResponseEntity.ok(completedTask);
            } else {
                return ResponseEntity.badRequest().body(null); 
            }
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(null); // You can provide an error response here
        }
    }
    
    @PutMapping("/pending/{taskId}")
    public ResponseEntity<Task> markTaskAsPending(@PathVariable int taskId) {
        try {
            Task task = taskService.getTaskById(taskId);
            
            // Set the task status to "pending"
            task.setStatus("pending");
            
            // Update the task in the database
            Task updatedTask = taskService.updateTask(taskId, task);
            
            return ResponseEntity.ok(updatedTask);
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(null); // You can provide an error response here
        }
    }
}

