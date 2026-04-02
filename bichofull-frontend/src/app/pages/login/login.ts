import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  showPassword = signal<boolean>(false);

  errorMessage = signal<string | null>(null);

  togglePassword() {
    this.showPassword.set(!this.showPassword());
  }

  constructor(private authService: AuthService,
              private router: Router
  ) {}

  login() {

    this.errorMessage.set(null);

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
        const msg = err.error?.message || "Erro ao conectar com o servidor.";
        
        this.errorMessage.set(msg);
        
        // Opcional: Alerta rápido para teste
        alert(msg);

      }
    });

  }

}