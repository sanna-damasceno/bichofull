import { Component, signal, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { NextDrawComponent } from '../../components/next-draw/next-draw';
import { DrawHistoryComponent } from '../../components/draw-history/draw-history';
import { AnimalService } from '../../services/animal.service';

@Component({
  selector: 'app-draws',
  standalone: true,
  imports: [CommonModule, NextDrawComponent, DrawHistoryComponent],
  templateUrl: './draws.html',
  styleUrl: './draws.css',
})
export class DrawsComponent implements OnInit {

  private drawService = inject(DrawService);
  private animalService = inject(AnimalService);

  history = signal<DrawResponse[]>([]);
  animals: any[] = [];

  ngOnInit() {
    this.animalService.getAnimals().subscribe({
      next: (data) => {
        this.animals = data;
        this.loadHistory();
      },
      error: (err) => console.error('Erro ao carregar animais:', err)
    });
  }

  loadHistory() {
    this.drawService.getDrawHistory().subscribe({
      next: (res) => this.history.set(res),
      error: (err) => console.error('Erro ao carregar histórico:', err)
    });
  }

  getAnimalPath(num: string | number): string {
    if (!num || this.animals.length === 0) return 'default';
    
    const val = num.toString();
    // Pega os últimos 2 dígitos (dezena)
    const lastTwoDigits = val.slice(-2).padStart(2, '0');
    
    // Tipagem explícita (a: any) para evitar erro de implicit any
    const found = this.animals.find((a: any) => a.dezenas.includes(lastTwoDigits));
    
    // Retorna o grupo com zero à esquerda (ex: 01, 02...)
    return found ? found.groupNumber.toString().padStart(2, '0') : 'default';
  }

}