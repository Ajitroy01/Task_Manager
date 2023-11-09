import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../task.service';
import { Task } from '../models/task.model';

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.css'],
})
export class TaskUpdateComponent implements OnInit {
  taskForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private taskService: TaskService
  ) {
    this.taskForm = this.formBuilder.group({
      taskId: [null, Validators.required], // Add taskId field
      title: ['', Validators.required],
      description: ['', Validators.required],
      dueDate: ['', Validators.required],
      priority: ['High', Validators.required],
      status: ['To Do', Validators.required],
    });
  }

  ngOnInit() {}

  updateTask() {
    if (this.taskForm?.valid) {
      const taskId = this.taskForm?.get('taskId')?.value;

      if (taskId !== null && taskId !== undefined) {
        // Fetch the user's ID from local storage
        const user = localStorage.getItem('currentUser');
        if (user) {
          const userObject = JSON.parse(user);
          const userId = userObject.id;

          // Create taskData and set user ID
          const taskData: Task = this.taskForm.value;
          taskData.user = { id: userId };

          // Update the task and handle errors
          this.taskService.updateTask(taskId, taskData).subscribe(
            () => {
              alert('Task updated successfully!');
            },
            (error) => {
              console.error('Error updating task:', error);
              alert(
                'An error occurred while updating the task. Please try again.'
              );
            }
          );

          // Reset the form after updating
          this.taskForm.reset();
        } else {
          console.error('Task ID is null or undefined');
          alert('Task ID is missing or invalid. Please try again.');
        }
      } else {
        console.error('Form is not valid');
        alert('The form is not valid. Please fill in all required fields.');
      }
    }
  }
}
