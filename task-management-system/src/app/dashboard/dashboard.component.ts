import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { TaskService } from '../task.service';
import { Task } from '../models/task.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  currentComponent: string = 'dashboard';
  currentUser: any;
  endingSoonTask: Task | null = null;
  totalTasks: number = 0;
  pendingTasksCount: number = 0;
  highMediumPriorityTasksCount: number = 0;

  constructor(
    private authService: AuthService,
    private taskService: TaskService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Fetch the user's information
    this.currentUser = this.authService.getUserData();

    // Fetch tasks for the logged-in user
    if (this.currentUser && this.currentUser.id) {
      this.taskService
        .getTasksByUserId(this.currentUser.id)
        .subscribe((tasks) => {
          this.totalTasks = tasks.length;

          const today = new Date();
          const endingSoonDate = new Date();
          endingSoonDate.setDate(today.getDate() + 3); // Adjust the days as needed
          this.endingSoonTask = this.getEndingSoonTask(tasks, endingSoonDate);
          this.pendingTasksCount = this.getPendingTasksCount(tasks);
          this.highMediumPriorityTasksCount =
            this.getHighMediumPriorityTasksCount(tasks);
        });
    }
  }

  toggleComponent(componentName: string): void {
    this.currentComponent = componentName;
  }

  private getEndingSoonTask(tasks: Task[], endingSoonDate: Date): Task | null {
    const endingSoonTasks = tasks.filter(
      (task) => new Date(task.dueDate) <= endingSoonDate
    );
    return endingSoonTasks.length > 0 ? endingSoonTasks[0] : null;
  }

  private getPendingTasksCount(tasks: Task[]): number {
    return tasks.filter((task) => task.status === 'pending').length;
  }

  private getHighMediumPriorityTasksCount(tasks: Task[]): number {
    return tasks.filter(
      (task) => task.priority === 'high' || task.priority === 'medium'
    ).length;
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.router.navigate(['/homepage']);
  }
}
