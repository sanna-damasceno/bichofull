import { Component } from '@angular/core';

import { NavbarComponent } from '../../components/navbar/navbar';
import { WalletComponent } from '../../components/wallet/wallet';
import { AnimalGridComponent } from '../../components/animal-grid/animal-grid';
import { BetFormComponent } from '../../components/bet-form/bet-form';
import { DrawHistoryComponent } from '../../components/draw-history/draw-history';
import { NextDrawComponent } from '../../components/next-draw/next-draw';

export interface Animal {
  id: number;
  groupNumber: number;
  name: string;
  dezenas: string;
}

@Component({
  selector: 'app-dashboard',
  imports: [
            WalletComponent,
            AnimalGridComponent,
            BetFormComponent,
            DrawHistoryComponent,
            NextDrawComponent
            ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent {
  selectedAnimal: Animal | null = null;


  onAnimalSelected(animal: Animal) {
    this.selectedAnimal = animal;
    console.log("Animal escolhido:", animal);
  }
}
