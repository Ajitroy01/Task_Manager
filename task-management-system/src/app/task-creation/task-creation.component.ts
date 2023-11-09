import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../task.service';
import { Task } from '../models/task.model';

@Component({
  selector: 'app-task-creation',
  templateUrl: './task-creation.component.html',
  styleUrls: ['./task-creation.component.css'],
})
export class TaskCreationComponent implements OnInit {
  taskForm: FormGroup; // Initialize taskForm with an empty FormGroup

  constructor(
    private formBuilder: FormBuilder,
    private taskService: TaskService
  ) {
    this.taskForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      dueDate: ['', Validators.required],
      priority: ['high', Validators.required],
      status: ['pending', Validators.required],
    });
  }

  ngOnInit() {}

  createTask() {
    if (this.taskForm.valid) {
      const taskData: Task = this.taskForm.value as Task;

      // Fetch the user's ID from local storage
      const user = localStorage.getItem('currentUser');
      if (user) {
        const userObject = JSON.parse(user);
        const userId = userObject.id;
        taskData.user = { id: userId };
      }

      this.taskService.createTask(taskData).subscribe(
        (response) => {
          alert('Task created successfully');
          this.taskForm.reset();
        },
        (error) => {
          alert('An error occurred while creating the task. Please try again.');
          console.error('Error creating task:', error);
        }
      );
    }
  }
}
