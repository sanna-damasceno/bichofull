import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-draws',
  imports: [CommonModule],
  templateUrl: './draws.html',
  styleUrl: './draws.css',
})
export class DrawsComponent implements OnInit, OnDestroy {

  sub!: Subscription;

  nextDrawTime!: Date;

  timeLeft = {
    hours: '00',
    minutes: '00',
    seconds: '00'
  };

  interval: any;

  lastDraw: DrawResponse | null = null;
  history: DrawResponse[] = [];

  constructor(
    private drawService: DrawService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.calculateNextDraw();
    this.startCountdown();

    this.loadData();

    //  atualiza ao navegar
    this.sub = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
    clearInterval(this.interval);
  }

  //  centraliza carregamento
  loadData() {
    this.loadLastDraw();
    this.loadHistory();
  }

  calculateNextDraw() {
    const now = new Date();

    const today11 = new Date();
    today11.setHours(11, 0, 0);

    const today14 = new Date();
    today14.setHours(14, 0, 0);

    const today18 = new Date();
    today18.setHours(18, 0, 0);

    if (now < today11) {
      this.nextDrawTime = today11;
    } else if (now < today14) {
      this.nextDrawTime = today14;
    } else if (now < today18) {
      this.nextDrawTime = today18;
    } else {
      const tomorrow = new Date();
      tomorrow.setDate(now.getDate() + 1);
      tomorrow.setHours(11, 0, 0);
      this.nextDrawTime = tomorrow;
    }
  }

  startCountdown() {
    this.interval = setInterval(() => {

      const now = new Date().getTime();
      const diff = this.nextDrawTime.getTime() - now;

      if (diff <= 0) {
        this.calculateNextDraw();
        this.loadLastDraw(); 
        return;
      }

      this.timeLeft = {
        hours: String(Math.floor(diff / (1000 * 60 * 60))).padStart(2, '0'),
        minutes: String(Math.floor((diff / (1000 * 60)) % 60)).padStart(2, '0'),
        seconds: String(Math.floor((diff / 1000) % 60)).padStart(2, '0')
      };

      this.cdr.detectChanges();

    }, 1000);
  }

  loadLastDraw() {
    this.drawService.getLastDraw().subscribe({
      next: (res) => this.lastDraw = res,
      error: (err) => console.error('Erro ao carregar último sorteio', err)
    });
  }

  loadHistory() {
    this.drawService.getDrawHistory().subscribe({
      next: (res) => this.history = res,
      error: (err) => console.error('Erro ao carregar histórico', err)
    });
  }

  getAnimal(number: string): string {
    const group = Math.ceil(parseInt(number.slice(2)) / 4);

    const animals = [
      'Avestruz','Águia','Burro','Borboleta','Cachorro',
      'Cabra','Carneiro','Camelo','Cobra','Coelho',
      'Cavalo','Elefante','Galo','Gato','Jacaré',
      'Leão','Macaco','Porco','Pavão','Peru',
      'Touro','Tigre','Urso','Veado','Vaca'
    ];

    return animals[group - 1] || '';
  }

  getNextDrawLabel(): string {
    return this.nextDrawTime.toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}