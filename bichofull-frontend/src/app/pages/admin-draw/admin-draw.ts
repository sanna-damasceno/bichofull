import { Component, OnInit, signal } from '@angular/core';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-draw',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-draw.html',
  styleUrls: ['./admin-draw.css'],
})

export class AdminDrawComponent {

  result = signal<DrawResponse | null>(null);
  loading = signal(false);
  success = signal(false);

  manualDraw = {
    firstPrize: '',
    secondPrize: '',
    thirdPrize: '',
    fourthPrize: '',
    fifthPrize: ''
  };


  constructor(
    private drawService: DrawService,
    private router: Router

  ) {}


  runDraw() {
    this.loading.set(true);
    this.success.set(false);

    this.drawService.runDraw().subscribe({
      next: (res) => {
        this.result.set(res);
        this.success.set(true);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.loading.set(false);
        alert('Erro ao executar sorteio');
      }
    });
  }

  resetState() {
    this.result.set(null);
    this.success.set(false);
    this.loading.set(false);
  }

  createManualDraw() {
    this.loading.set(true);
    this.success.set(false);

    this.drawService.createDraw(this.manualDraw).subscribe({
      next: (res) => {
        this.result.set(res);
        this.success.set(true);
        this.loading.set(false);

        this.resetManualForm();
      },
      error: (err) => {
        console.error(err);
        this.loading.set(false);
        alert('Erro ao criar sorteio manual');
      }
    });
  }

  resetManualForm() {
    this.manualDraw = {
      firstPrize: '',
      secondPrize: '',
      thirdPrize: '',
      fourthPrize: '',
      fifthPrize: ''
    };
  }


  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}