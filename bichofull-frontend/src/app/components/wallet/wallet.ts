import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { BetService, BetResponse } from '../../services/bet.service';

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wallet.html',
  styleUrl: './wallet.css',
})
export class WalletComponent {

  private userService = inject(UserService);
  private betService = inject(BetService);

  bets = signal<BetResponse[]>([]);

  wallet = computed(() => {
    const user = this.userService.user();

    return {
      balance: user?.balance ?? 0,
      totalWon: user?.totalWon ?? 0,
      totalLost: user?.totalLost ?? 0,
      totalPending: user?.totalPending ?? 0
    };
  });

  constructor() {
    this.userService.loadUserProfile();

    this.betService.getMyBets().subscribe(bets => {
      this.bets.set(bets);
    });
  }
}