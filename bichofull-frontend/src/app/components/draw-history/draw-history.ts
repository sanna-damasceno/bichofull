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
    setInterval(() => this.loadLastDraw(), 5000);
  }

  loadLastDraw() {
    this.drawService.getLastDraw().subscribe({
      next: (res) => this.lastDraw.set(res),
      error: (err) => console.error('Erro ao carregar sorteio:', err)
    });
  }

  getAnimalImage(num: string | undefined | null): string {
    if (!num) return 'assets/animals/01.png'; 

    const numStr = String(num);
    const dezena = parseInt(numStr.slice(-2));
    const calcDezena = (dezena === 0) ? 100 : dezena;
    const groupId = Math.ceil(calcDezena / 4);
    const fileName = String(groupId).padStart(2, '0');

    return `assets/animals/${fileName}.png`;
  }

  getAnimalName(num: string | undefined | null): string {
    if (!num) return 'Aguardando...';
    
    const numStr = String(num);
    const dezena = parseInt(numStr.slice(-2)) || 0;
    const calcDezena = (dezena === 0) ? 100 : dezena;
    const group = Math.ceil(calcDezena / 4);
    
    const animals = [
      'Avestruz','Águia','Burro','Borboleta','Cachorro',
      'Cabra','Carneiro','Camelo','Cobra','Coelho',
      'Cavalo','Elefante','Galo','Gato','Jacaré',
      'Leão','Macaco','Porco','Pavão','Peru',
      'Touro','Tigre','Urso','Veado','Vaca'
    ];
    
    return animals[group - 1] || 'Desconhecido';
  }
}