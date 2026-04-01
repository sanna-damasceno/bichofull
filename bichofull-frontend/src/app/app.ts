import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { FooterComponent } from './components/footer/footer';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FooterComponent, CommonModule],
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