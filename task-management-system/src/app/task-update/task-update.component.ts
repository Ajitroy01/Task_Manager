import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../task.service';

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
        this.taskService.updateTask(taskId, this.taskForm.value).subscribe(
          () => {
            // Alert the user upon successful update
            alert('Task updated successfully!');
          },
          (error) => {
            console.error('Error updating task:', error);
          }
        );
      } else {
        console.error('Task ID is null or undefined');
      }
    } else {
      console.error('Form is not valid');
    }
  }
}
