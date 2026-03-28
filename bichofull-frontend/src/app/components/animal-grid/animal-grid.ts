import { Component, OnInit, Output, EventEmitter, signal } from '@angular/core'; // Importe o signal
import { CommonModule } from '@angular/common';
import { AnimalService } from '../../services/animal.service';
import { Animal } from '../../pages/dashboard/dashboard';

@Component({
  selector: 'app-animal-grid',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './animal-grid.html',
  styleUrl: './animal-grid.css'
})
export class AnimalGridComponent implements OnInit {
  @Output() animalSelected = new EventEmitter<any>();


  animals = signal<any[]>([]);

  constructor(private animalService: AnimalService) {}

  ngOnInit(): void {
    this.animalService.getAnimals().subscribe({
      next: (data: any[]) => {
        this.animals.set(data);
      },
      error: (err: any) => console.error('Erro:', err)
    });
  }

  selectAnimal(animal: any) {
    this.animalSelected.emit(animal);
  }
}