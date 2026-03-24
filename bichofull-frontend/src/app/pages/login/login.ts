import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  constructor(private authService: AuthService,
              private router: Router
  ) {}

  login() {

    console.log("BOTÃO CLICADO");

    this.authService.login(this.email, this.password).subscribe({
      next: (response: any) => {

        localStorage.setItem("token", response.token);

        console.log("LOGIN FUNCIONOU", response);

        const token = response.token;
        const payload = JSON.parse(atob(token.split('.')[1]));

        const role = payload.role;

        if (role === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/dashboard']);
        }

      },
      error: (err) => {

        console.log("ERRO NO LOGIN", err);

      }
    });

  }

}