import { Component, Input, OnChanges, SimpleChanges, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BetService, BetRequest, BetResponse } from '../../services/bet.service';
import { UserService } from '../../services/user.service';

type Animal = {
  id: number;
  name: string;
  dezenas: number[];
};

@Component({
  selector: 'app-bet-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bet-form.html',
  styleUrl: './bet-form.css',
})
export class BetFormComponent implements OnChanges {
  @Input() selectedAnimal: Animal | null = null;

  betType: 'GRUPO' | 'DEZENA' | 'MILHAR' = 'GRUPO';
  numberInput = signal('');
  detectedAnimal: Animal | null = null;
  amount: number = 0;
  prize: number = 0;

  animals: Animal[] = [
    { id: 1, name: "Avestruz", dezenas: [1, 2, 3, 4] },
    { id: 2, name: "Águia", dezenas: [5, 6, 7, 8] },
    { id: 3, name: "Burro", dezenas: [9, 10, 11, 12] },
    { id: 4, name: "Borboleta", dezenas: [13, 14, 15, 16] },
    { id: 5, name: "Cachorro", dezenas: [17, 18, 19, 20] },
    { id: 6, name: "Cabra", dezenas: [21, 22, 23, 24] },
    { id: 7, name: "Carneiro", dezenas: [25, 26, 27, 28] },
    { id: 8, name: "Camelo", dezenas: [29, 30, 31, 32] },
    { id: 9, name: "Cobra", dezenas: [33, 34, 35, 36] },
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

  constructor(private betService: BetService,
              private userService: UserService
  ) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedAnimal'] && this.selectedAnimal) {
      this.betType = 'GRUPO';
      this.numberInput.set(this.selectedAnimal.id.toString().padStart(2, '0'));
      this.detectedAnimal = this.selectedAnimal;
      
      setTimeout(() => {
        this.calculatePrize();
      });
    }
  }

  onTypeChange(type: 'GRUPO' | 'DEZENA' | 'MILHAR') {
    this.betType = type;
    this.numberInput.set('');
    this.detectedAnimal = null;
    this.prize = 0;
  }

  onNumberChange(value: string) {
    const cleanValue = value.replace(/\D/g, '');
    const limit = this.betType === 'MILHAR' ? 4 : 2;

    this.numberInput.set(cleanValue.slice(0, limit));

    if (!this.numberInput()) {
      this.detectedAnimal = null;
      this.calculatePrize();
      return;
    }

    this.updateDetectedAnimal();
    this.calculatePrize();
  }

  private updateDetectedAnimal() {
    const val = parseInt(this.numberInput());
    if (this.betType === 'GRUPO') {
      this.detectedAnimal = this.animals.find(a => a.id === val) || null;
    } else {
      const lastTwoDigits = parseInt(this.numberInput().slice(-2));
      this.detectedAnimal = this.animals.find(a => a.dezenas.includes(lastTwoDigits)) || null;
    }
  }

  calculatePrize() {
    if (!this.amount || this.amount <= 0 || !this.detectedAnimal) {
      this.prize = 0;
      return;
    }
    const multipliers = { 'GRUPO': 18, 'DEZENA': 60, 'MILHAR': 4000 };
    this.prize = this.amount * multipliers[this.betType];
  }

  confirmBet(): void {
    if (!this.detectedAnimal || this.amount <= 0 || !this.numberInput()) {
      alert('Please fill in all fields correctly.');
      return;
    }

    const typeMap: Record<string, 'GROUP' | 'TEN' | 'THOUSAND'> = {
      'GRUPO': 'GROUP',
      'DEZENA': 'TEN',
      'MILHAR': 'THOUSAND'
    };

    const betRequest: BetRequest = {
      type: typeMap[this.betType],
      chosenNumber: this.numberInput(),
      amount: this.amount
    };

    this.betService.placeBet(betRequest).subscribe({
      next: (response: BetResponse) => {
        console.log('Bet successful:', response);
        this.userService.loadUserProfile();
        alert(`Success! Bet ID: ${response.id} registered.`);
        this.resetForm();
      },
      error: (err) => {
        console.error('Error:', err);
        alert('Failed to place bet. Please check your balance or connection.');
      }
    });
  }

  private resetForm(): void {
    this.numberInput.set('');
    this.detectedAnimal = null;
    this.amount = 0;
    this.prize = 0;
  }
}