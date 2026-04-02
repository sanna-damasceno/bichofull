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
  
  // Transformado em Signal para evitar NG0100
  detectedAnimal = signal<Animal | null>(null);
  
  amount: number = 0;
  prize: number = 0;

  animals: Animal[] = [];

  constructor(
    private betService: BetService,
    private userService: UserService,
    private animalService: AnimalService
  ) {}

  ngOnInit(): void {
    this.animalService.getAnimals().subscribe({
      next: (data) => (this.animals = data),
      error: (err) => console.error('Erro ao carregar animais no form:', err),
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedAnimal'] && this.selectedAnimal) {
      this.betType = 'GRUPO';
      const displayNum = this.selectedAnimal.groupNumber.toString().padStart(2, '0');
      this.numberInput.set(displayNum);
      
      // Atualiza o Signal
      this.detectedAnimal.set(this.selectedAnimal);
      
      // Pequeno delay para garantir que o cálculo ocorra após a atualização do input
      setTimeout(() => this.calculatePrize());
    }
  }

  onTypeChange(type: 'GRUPO' | 'DEZENA' | 'MILHAR') {
    this.betType = type;
    this.numberInput.set('');
    this.detectedAnimal.set(null);
    this.prize = 0;
    this.errorMessage.set(null);
  }

  onNumberChange(value: string) {
    this.errorMessage.set(null);
    const cleanValue = value.replace(/\D/g, '');
    const limit = this.betType === 'MILHAR' ? 4 : 2;

    if (cleanValue.length > limit) return;

    this.numberInput.set(cleanValue);

    if (!cleanValue) {
      this.detectedAnimal.set(null);
      this.calculatePrize();
      return;
    }

    this.updateDetectedAnimal();
    this.calculatePrize();
  }

  private updateDetectedAnimal() {
    const val = this.numberInput();
    let found: Animal | null = null;

    if (this.betType === 'GRUPO') {
      const num = parseInt(val, 10);
      if (num >= 1 && num <= 25) {
        found = this.animals.find((a) => a.groupNumber === num) || null;
      } else if (val.length >= 1) { // Mudado de 2 para 1
        this.errorMessage.set('Grupo deve estar entre 01 e 25');
      }
    } else {
      // Para DEZENA ou MILHAR
      if (val.length >= 1) { // Mudado de 2 para 1 para aceitar "2" como "02"
        // Pegamos o valor e garantimos que tenha pelo menos 2 dígitos para a busca
        const formattedValue = val.padStart(2, '0');
        const lastTwoDigits = formattedValue.slice(-2);
        
        found = this.animals.find((a) => a.dezenas.includes(lastTwoDigits)) || null;
      }
    }

    this.detectedAnimal.set(found);
  }

  calculatePrize() {
    // Acessa o valor do Signal usando ()
    const animal = this.detectedAnimal();
    
    if (!this.amount || this.amount <= 0 || !animal) {
      this.prize = 0;
      return;
    }
    const multipliers = { GRUPO: 18, DEZENA: 60, MILHAR: 4000 };
    this.prize = this.amount * multipliers[this.betType];
  }

  confirmBet(): void {
    if (this.errorMessage()) return;

    const val = this.numberInput();
    const animal = this.detectedAnimal();

    if (this.betType === 'MILHAR' && val.length !== 4) {
      alert('Milhar precisa ter 4 números');
      return;
    }

    if ((this.betType === 'GRUPO' || this.betType === 'DEZENA') && val.length < 1) {
      alert('Preencha o número para apostar');
      return;
    }

    if (!animal || this.amount <= 0) {
      alert('Preencha todos os campos corretamente.');
      return;
    }

    const typeMap: Record<string, 'GROUP' | 'TEN' | 'THOUSAND'> = {
      GRUPO: 'GROUP',
      DEZENA: 'TEN',
      MILHAR: 'THOUSAND',
    };

    const betRequest: BetRequest = {
      type: typeMap[this.betType],
      chosenNumber: val.padStart(this.betType === 'MILHAR' ? 4 : 2, '0'),
      amount: this.amount,
    };

    this.betService.placeBet(betRequest).subscribe({
      next: (response: BetResponse) => {
        this.userService.loadUserProfile();
        alert(`Sucesso! Aposta registrada.`);
        this.resetForm();
      },
      error: (err) => alert('Erro ao realizar aposta. Verifique seu saldo.'),
    });
  }

  getPlaceholder(): string {
    switch (this.betType) {
      case 'GRUPO':
        return '1-25';
      case 'DEZENA':
        return '00-99';
      case 'MILHAR':
        return '0000-9999';
      default:
        return '00';
    }
  }

  private resetForm(): void {
    this.numberInput.set('');
    this.detectedAnimal.set(null);
    this.amount = 0;
    this.prize = 0;
  }
}