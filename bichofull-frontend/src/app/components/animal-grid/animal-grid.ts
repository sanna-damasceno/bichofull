import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-animal-grid',
  imports: [CommonModule],
  templateUrl: './animal-grid.html',
  styleUrl: './animal-grid.css'
})


export class AnimalGridComponent {
  @Output() animalSelected = new EventEmitter<any>();


  animals = [
    { id: 1,  name: "Avestruz", dezenas: [1, 2, 3, 4] },
    { id: 2,  name: "Águia", dezenas: [5, 6, 7, 8] },
    { id: 3,  name: "Burro", dezenas: [9, 10, 11, 12] },
    { id: 4,  name: "Borboleta", dezenas: [13, 14, 15, 16] },
    { id: 5,  name: "Cachorro", dezenas: [17, 18, 19, 20] },
    { id: 6,  name: "Cabra", dezenas: [21, 22, 23, 24] },
    { id: 7,  name: "Carneiro", dezenas: [25, 26, 27, 28] },
    { id: 8,  name: "Camelo", dezenas: [29, 30, 31, 32] },
    { id: 9,  name: "Cobra", dezenas: [33, 34, 35, 36] },
    { id: 10, name: "Coelho", dezenas: [37, 38, 39, 40] },
    { id: 11, name: "Cavalo", dezenas: [41, 42, 43, 44] },
    { id: 12, name: "Elefante", dezenas: [45, 46, 47, 48] },
    { id: 13, name: "Galo", dezenas: [49, 50, 51, 52] },
    { id: 14, name: "Gato", dezenas: [53, 54, 55, 56] },
    { id: 15, name: "Jacaré", dezenas: [57, 58, 59, 60] },
    { id: 16, name: "Leão", dezenas: [61, 62, 63, 64] },
    { id: 17, name: "Macaco", dezenas: [65, 66, 67, 68] },
    { id: 18, name: "Porco", dezenas: [69, 70, 71, 72] },
    { id: 19, name: "Pavão", dezenas: [73, 74, 75, 76] },
    { id: 20, name: "Peru", dezenas: [77, 78, 79, 80] },
    { id: 21, name: "Touro", dezenas: [81, 82, 83, 84] },
    { id: 22, name: "Tigre", dezenas: [85, 86, 87, 88] },
    { id: 23, name: "Urso", dezenas: [89, 90, 91, 92] },
    { id: 24, name: "Veado", dezenas: [93, 94, 95, 96] },
    { id: 25, name: "Vaca", dezenas: [97, 98, 99, 0] }
  ];

  selectAnimal(animal: any) {
    this.animalSelected.emit(animal);
  }


}