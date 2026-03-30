import { Component, signal, inject, computed, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { NextDrawComponent } from '../../components/next-draw/next-draw';
import { DrawHistoryComponent } from '../../components/draw-history/draw-history';
import { AnimalService } from '../../services/animal.service';

@Component({
  selector: 'app-draws',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './draws.html',
  styleUrl: './draws.css',
})
export class DrawsComponent implements OnInit {

  private drawService = inject(DrawService);
  private animalService = inject(AnimalService);

  history = signal<DrawResponse[]>([]);
  animals: any[] = [];

  currentPage = signal(1);
  pageSize = 5;

  // Sorteios paginados calculados automaticamente
  paginatedHistory = computed(() => {
    const startIndex = (this.currentPage() - 1) * this.pageSize;
    return this.history().slice(startIndex, startIndex + this.pageSize);
  });

  totalPages = computed(() => Math.ceil(this.history().length / this.pageSize));

  pagesArray = computed(() => 
    Array.from({ length: this.totalPages() }, (_, i) => i + 1)
  );

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
      next: (res) => {
        this.history.set(res);
        this.currentPage.set(1);
      }, 
      error: (err) => console.error('Erro ao carregar histórico:', err)
    });
  }

  changePage(page: number) {
    if (page >= 1 && page <= this.totalPages()) {
      this.currentPage.set(page);
    }
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