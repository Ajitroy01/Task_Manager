import { Component, OnInit } from '@angular/core';
import { TaskService } from '../task.service';
import { Task } from '../models/task.model';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
})
export class TaskListComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private taskService: TaskService) {}

  ngOnInit() {
    this.fetchTasks(); // Fetch tasks when the component initializes
  }

  fetchTasks() {
    // Fetch tasks for the logged-in user using their user ID from local storage
    const userStr = localStorage.getItem('currentUser');
    if (userStr) {
      const user = JSON.parse(userStr);
      if (user) {
        const userId = user.id;
        this.taskService.getTasksByUserId(userId).subscribe((tasks) => {
          this.tasks = tasks;
        });
      }
    }
  }

  deleteTask(taskId: number) {
    // Delete the task using the task service
    this.taskService.deleteTask(taskId).subscribe(
      (response) => {
        // Handle the response (e.g., display a success message or perform any additional action)
        console.log(`Task with ID ${taskId} deleted successfully.`);
        this.fetchTasks(); // Refresh the tasks list after deletion
      },
      (error) => {
        // Handle the error (e.g., display an error message)
        console.error('Error deleting the task:', error);
      }
    );
  }

  markAsCompleted(taskId: number) {
    // Update the task status to "Pending" using the task service
    this.taskService.markTaskAsCompleted(taskId).subscribe(
      (response) => {
        // Handle the response (e.g., display a success message or perform any additional action)
        console.log(`Task with ID ${taskId} marked as completed.`);
        this.fetchTasks(); // Refresh the tasks list after marking as pending
      },
      (error) => {
        // Handle the error (e.g., display an error message)
        console.error('Error marking the task as completed:', error);
      }
    );
  }
}
