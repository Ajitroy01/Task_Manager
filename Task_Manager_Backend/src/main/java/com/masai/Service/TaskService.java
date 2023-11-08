package com.masai.Service;

import java.util.List;

import com.masai.Entity.Task;
import com.masai.Exception.TaskException;

public interface TaskService{
	Task createTask(Task task) throws TaskException;

    Task updateTask(int taskId, Task task) throws TaskException;

    void deleteTaskById(int taskId) throws TaskException;

    List<Task> getTasksByUserId(int userId);

    Task getTaskById(int taskId) throws TaskException;
}
