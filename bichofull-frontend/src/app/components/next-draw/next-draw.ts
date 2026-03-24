import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-next-draw',
  imports: [CommonModule],
  templateUrl: './next-draw.html',
  styleUrl: './next-draw.css',
})


export class NextDrawComponent implements OnInit, OnDestroy {

  sub!: Subscription;

  nextDrawTime!: Date;

  timeLeft = {
    hours: '00',
    minutes: '00',
    seconds: '00'
  };

  interval: any;

  constructor(
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.calculateNextDraw();
    this.startCountdown();


    this.sub = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.calculateNextDraw();
      }
    });
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
    clearInterval(this.interval);
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

  getNextDrawLabel(): string {
    return this.nextDrawTime.toLocaleTimeString([], {
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}