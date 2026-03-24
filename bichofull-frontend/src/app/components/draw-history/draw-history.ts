import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrawService, DrawResponse } from '../../services/draw.service';

@Component({
  selector: 'app-draw-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './draw-history.html',
  styleUrl: './draw-history.css',
})
export class DrawHistoryComponent implements OnInit {

  lastDraw = signal<DrawResponse | null>(null);

  constructor(private drawService: DrawService) {}

  ngOnInit() {
    this.loadLastDraw();

    // auto refresh
    setInterval(() => {
      this.loadLastDraw();
    }, 5000);
  }

  loadLastDraw() {
    this.drawService.getLastDraw().subscribe({
      next: (res) => this.lastDraw.set(res),
      error: (err) => console.error(err)
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
}