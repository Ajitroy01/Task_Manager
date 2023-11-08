import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  currentComponent: string = 'dashboard';
  currentUser: any;

  constructor(private authService: AuthService) {}
  ngOnInit(): void {
    this.currentUser = this.authService.getUserData();
  }
  toggleComponent(componentName: string): void {
    this.currentComponent = componentName;
  }
}
