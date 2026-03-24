import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule], 
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  registerData = {
    name: '',
    email: '',
    password: ''
  };
  confirmPassword = ''; 

  constructor(private authService: AuthService, private router: Router) {}

  onRegister(event: Event) {
    event.preventDefault();

    // Validação extra por segurança
    if (this.registerData.password !== this.confirmPassword) {
      alert('As senhas precisam ser iguais!');
      return;
    }

    this.authService.register(this.registerData).subscribe({
      next: (res) => {
        alert('Conta criada! Saldo de R$ 1.000,00 liberado.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Erro no registro', err);
      }
    });
  }

}