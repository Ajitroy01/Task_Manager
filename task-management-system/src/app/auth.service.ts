import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './models/user.model'; // Import your User interface

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/users'; // Replace with your backend API URL

  constructor(private http: HttpClient) {}

  login(user: User): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: 'Basic ' + btoa(`${user.email}:${user.password}`),
    });

    return this.http.post<any>(`${this.apiUrl}/login`, {}, { headers });
  }

  register(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, user);
  }

  getUserData(): User | null {
    const userData = localStorage.getItem('currentUser');
    return userData ? JSON.parse(userData) : null;
  }
}
