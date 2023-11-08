import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from './models/task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/tasks';

  constructor(private http: HttpClient) {}

  createTask(taskData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, taskData);
  }
  getTasksByUserId(userId: string): Observable<Task[]> {
    // Fetch tasks by user ID
    return this.http.get<Task[]>(`${this.apiUrl}/list/${userId}`);
  }

  deleteTask(taskId: number): Observable<any> {
    // Delete a task by its ID
    return this.http.delete(`${this.apiUrl}/delete/${taskId}`);
  }

  markTaskAsCompleted(taskId: number): Observable<any> {
    // Update the status of a task to "Completed"
    return this.http.put(`${this.apiUrl}/complete/${taskId}`, null);
  }

  updateTask(taskId: number, updatedTaskData: any): Observable<any> {
    // Update a task using its ID and the updated task data
    return this.http.put<any>(
      `${this.apiUrl}/update/${taskId}`,
      updatedTaskData
    );
  }
  getTaskById(taskId: number): Observable<Task> {
    // Retrieve a specific task by its ID
    return this.http.get<Task>(`${this.apiUrl}/${taskId}`);
  }
}
