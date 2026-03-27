import { Component, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-next-draw',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './next-draw.html',
  styleUrl: './next-draw.css',
})
export class NextDrawComponent {

  now = signal(Date.now());

  nextDrawTime = computed(() => {
    const now = new Date(this.now());

    const times = [11, 14, 18];

    for (const h of times) {
      const t = new Date();
      t.setHours(h, 0, 0);
      if (now < t) return t;
    }

    const tomorrow = new Date();
    tomorrow.setDate(now.getDate() + 1);
    tomorrow.setHours(11, 0, 0);
    return tomorrow;
  });

  timeLeft = computed(() => {
    const diff = this.nextDrawTime().getTime() - this.now();

    if (diff <= 0) return { h: '00', m: '00', s: '00' };

    return {
      h: String(Math.floor(diff / 3600000)).padStart(2, '0'),
      m: String(Math.floor((diff % 3600000) / 60000)).padStart(2, '0'),
      s: String(Math.floor((diff % 60000) / 1000)).padStart(2, '0'),
    };
  });

  constructor() {
    setInterval(() => {
      this.now.set(Date.now());
    }, 1000);
  }
}