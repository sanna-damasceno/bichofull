import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { NextDrawComponent } from '../../components/next-draw/next-draw';
import { DrawHistoryComponent } from '../../components/draw-history/draw-history';

@Component({
  selector: 'app-draws',
  standalone: true,
  imports: [CommonModule, NextDrawComponent, DrawHistoryComponent],
  templateUrl: './draws.html',
  styleUrl: './draws.css',
})
export class DrawsComponent {

  history = signal<DrawResponse[]>([]);

  constructor(private drawService: DrawService) {
    this.loadHistory();
  }

  loadHistory() {
    this.drawService.getDrawHistory().subscribe({
      next: (res) => this.history.set(res),
      error: (err) => console.error(err)
    });
  }
}