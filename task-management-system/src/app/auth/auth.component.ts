import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { User } from '../models/user.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {
  loginForm: FormGroup;
  registerForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });

    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  ngOnInit() {
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    if (signUpButton && signInButton && container) {
      signUpButton.addEventListener('click', () => {
        container.classList.add('right-panel-active');
      });

      signInButton.addEventListener('click', () => {
        container.classList.remove('right-panel-active');
      });
    } else {
      console.error('One or more elements not found');
    }
  }

  login() {
    if (this.loginForm.valid) {
      const loginData: User = this.loginForm.value;
      this.authService.login(loginData).subscribe(
        (response) => {
          const userData = {
            id: response.userId,
            name: response.email,
          };
          localStorage.setItem('currentUser', JSON.stringify(userData));
          alert('Login successful.');
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          console.log(error);
          alert('An error occurred while logging in.');
          this.errorMessage = 'An error occurred while logging in.';
        }
      );
    }
  }

  register() {
    if (this.registerForm.valid) {
      const registerData: User = this.registerForm.value;
      this.authService.register(registerData).subscribe(
        (response) => {
          alert('Account created successfully. Please login.');
        },
        (error) => {
          alert('An error occurred during registration.');
          this.errorMessage = 'An error occurred during registration.';
        }
      );
    }
  }
}
