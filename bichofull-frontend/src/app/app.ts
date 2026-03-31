import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar'; // Ajuste o caminho se necessário
import { FooterComponent } from './components/footer/footer'; // Ajuste o caminho se necessário
import { CommonModule } from '@angular/common'; // Necessário para usar @if ou *ngIf

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, FooterComponent, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('bichofull-frontend');
  
  constructor(private router: Router) {}

  // Retorna true se estiver no login, registro ou admin
  isAuthPage() {
    const url = this.router.url;
    return url.includes('login') || url.includes('register') || url.includes('admin');
  }
}