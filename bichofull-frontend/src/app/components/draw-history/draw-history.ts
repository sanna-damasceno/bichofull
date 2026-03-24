import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { ChangeDetectorRef } from '@angular/core';


@Component({
  selector: 'app-draw-history',
  imports: [CommonModule],
  templateUrl: './draw-history.html',
  styleUrl: './draw-history.css',
})

export class DrawHistoryComponent implements OnInit, OnDestroy {

  lastDraw: DrawResponse | null = null;
  interval: any;

  constructor(
    private drawService: DrawService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadLastDraw();

    // 🔥 atualiza automaticamente a cada 5 segundos
    this.interval = setInterval(() => {
      this.loadLastDraw();
    }, 5000);
  }

  ngOnDestroy() {
    clearInterval(this.interval);
  }

  loadLastDraw() {
    this.drawService.getLastDraw().subscribe({
      next: (res) => {
        this.lastDraw = res;
        this.cdr.detectChanges(); // 🔥 força atualização na tela
      },
      error: (err) => console.error('Erro ao carregar último sorteio', err)
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