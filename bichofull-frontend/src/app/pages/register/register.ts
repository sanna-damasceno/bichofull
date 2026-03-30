import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule], 
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

  // Controle de visibilidade para os dois campos
  showPassword = signal<boolean>(false);
  showConfirmPassword = signal<boolean>(false);

  togglePassword() {
    this.showPassword.set(!this.showPassword());
  }

  toggleConfirmPassword() {
    this.showConfirmPassword.set(!this.showConfirmPassword());
  }


  constructor(private authService: AuthService, private router: Router) {}
  
  errorMessage = signal<string | null>(null);

  onRegister(event: Event) {
    event.preventDefault();

    if (!this.validateForm()) return;

    this.authService.register(this.registerData).subscribe({
      next: (res: any) => {
        alert('Conta criada! Saldo de R$ 1.000,00 liberado.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Erro no registro', err);

        if (err.error === 'Email already in use') {
          this.errorMessage.set('Este email já está cadastrado');
        } else {
          this.errorMessage.set('Erro ao cadastrar. Tente novamente.');
        }
      }
    });
  }

  validateForm(): boolean {
    this.errorMessage.set(null);

    if (!this.registerData.name.trim()) {
      this.errorMessage.set('Nome é obrigatório');
      return false;
    }

    if (!this.registerData.email) {
      this.errorMessage.set('Email é obrigatório');
      return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(this.registerData.email)) {
      this.errorMessage.set('Email inválido');
      return false;
    }

    if (!this.registerData.password) {
      this.errorMessage.set('Senha é obrigatória');
      return false;
    }

    if (this.registerData.password.length < 6) {
      this.errorMessage.set('Senha deve ter no mínimo 6 caracteres');
      return false;
    }

    if (this.registerData.password !== this.confirmPassword) {
      this.errorMessage.set('As senhas não coincidem');
      return false;
    }

    return true;
  }

}