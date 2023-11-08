package com.masai.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.Entity.Task;
import com.masai.Exception.TaskException;
import com.masai.Repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) throws TaskException {
        try {
            Task createdTask = taskRepository.save(task);
            logger.info("Task created successfully: " + createdTask.getId());
            return createdTask;
        } catch (Exception e) {
            logger.error("Error while creating task: " + e.getMessage());
            throw new TaskException("Error creating task");
        }
    }

    @Override
    public Task updateTask(int taskId, Task updatedTask) throws TaskException {
        try {
            if (!taskRepository.existsById(taskId)) {
                throw new TaskException("Task not found with ID: " + taskId);
            }

            updatedTask.setId(taskId);
            Task updated = taskRepository.save(updatedTask);
            logger.info("Task updated successfully: " + taskId);
            return updated;
        } catch (Exception e) {
            logger.error("Error while updating task: " + e.getMessage());
            throw new TaskException("Error updating task");
        }
    }

    @Override
    public void deleteTaskById(int taskId) throws TaskException {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskException("Task not found with ID: " + taskId);
        }

        taskRepository.deleteById(taskId);
        logger.info("Task deleted successfully: " + taskId);
    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task getTaskById(int taskId) throws TaskException {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            throw new TaskException("Task not found with ID: " + taskId);
        }
        return task;
    }
}
