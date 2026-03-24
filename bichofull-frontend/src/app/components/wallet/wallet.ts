import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService, UserResponse } from '../../services/user.service';

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wallet.html',
  styleUrl: './wallet.css',
})
export class WalletComponent {

  wallet = signal({
    balance: 0,
    totalWon: 0,
    totalLost: 0,
    totalPending: 0
  });

  constructor(private userService: UserService) {

    this.userService.user$.subscribe((data: UserResponse | null) => {
      if (data) {
        this.wallet.set({
          balance: data.balance,
          totalWon: data.totalWon,
          totalLost: data.totalLost,
          totalPending: data.totalPending
        });
      }
    });

    this.userService.loadUserProfile();
  }
}