import { Component, Input, OnChanges, SimpleChanges, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BetService, BetRequest, BetResponse } from '../../services/bet.service';
import { UserService } from '../../services/user.service';
import { AnimalService } from '../../services/animal.service';
import { Animal } from '../../pages/dashboard/dashboard';


@Component({
  selector: 'app-bet-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './bet-form.html',
  styleUrl: './bet-form.css',
})
export class BetFormComponent implements OnInit, OnChanges {

  errorMessage = signal<string | null>(null);

  @Input() selectedAnimal: Animal | null = null;

  betType: 'GRUPO' | 'DEZENA' | 'MILHAR' = 'GRUPO';
  numberInput = signal('');
  detectedAnimal: Animal | null = null;
  amount: number = 0;
  prize: number = 0;

  animals: Animal[] = [];

  constructor(private betService: BetService,
              private userService: UserService,
              private animalService: AnimalService
  ) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedAnimal'] && this.selectedAnimal) {
      this.betType = 'GRUPO';
      // Usa o groupNumber formatado com 2 dígitos (ex: 01, 02)
      const displayNum = this.selectedAnimal.groupNumber.toString().padStart(2, '0');
      this.numberInput.set(displayNum);
      this.detectedAnimal = this.selectedAnimal;
      
      setTimeout(() => this.calculatePrize());
    }
  }

  onTypeChange(type: 'GRUPO' | 'DEZENA' | 'MILHAR') {
    this.betType = type;
    this.numberInput.set('');
    this.detectedAnimal = null;
    this.prize = 0;
  }

  onNumberChange(value: string) {
    this.errorMessage.set(null);

    const cleanValue = value.replace(/\D/g, '');
    const limit = this.betType === 'MILHAR' ? 4 : 2;

    if (cleanValue.length > limit) {
      return;
    }

    this.numberInput.set(cleanValue);

    if (this.betType === 'GRUPO' && cleanValue.length === 2) {
      const num = parseInt(cleanValue);

      if (num < 1 || num > 25) {
        this.errorMessage.set('Grupo deve estar entre 01 e 25');
        this.detectedAnimal = null;
        return;
      }
    }

    if (!cleanValue) {
      this.detectedAnimal = null;
      this.calculatePrize();
      return;
    }

    this.updateDetectedAnimal();
    this.calculatePrize();
  }

  ngOnInit(): void {
    
    this.animalService.getAnimals().subscribe({
      next: (data) => this.animals = data,
      error: (err) => console.error('Erro ao carregar animais no form:', err)
    });
  }

  private updateDetectedAnimal() {
    const val = this.numberInput();
    if (!val) {
      this.detectedAnimal = null;
      return;
    }

    if (this.betType === 'GRUPO') {
      const num = parseInt(val); // Definindo o 'num' que faltava
      this.detectedAnimal = this.animals.find(a => a.groupNumber === num) || null;
    } else {
      const lastTwoDigits = val.slice(-2).padStart(2, '0');

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

    if (this.errorMessage()) {
      alert(this.errorMessage());
      return;
    }

    if (this.betType === 'MILHAR' && this.numberInput().length !== 4) {
      alert('Milhar precisa ter 4 números');
      return;
    }

    if ((this.betType === 'GRUPO' || this.betType === 'DEZENA') && this.numberInput().length !== 2) {
      alert('Grupo/Dezena devem ter apenas números');
      return;
    }

    if (!this.detectedAnimal || this.amount <= 0 || !this.numberInput()) {
      alert('Preencha todos os campos corretamente.');
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