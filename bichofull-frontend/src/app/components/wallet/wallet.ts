import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { WalletService, WalletDTO } from '../../services/wallet.service';
import { UserService, UserResponse } from '../../services/user.service'; 

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './wallet.html',
  styleUrl: './wallet.css',
})
export class WalletComponent implements OnInit {
  // Inicializamos com valores zerados para evitar erros de undefined no HTML
  wallet: WalletDTO = { balance: 0, totalWon: 0, totalLost: 0, totalPending: 0 };

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    // 1. Escuta o BehaviorSubject. Sempre que o perfil mudar, a tela atualiza.
    this.userService.user$.subscribe(data => {
      if (data) {
        console.log('Dados recebidos do Backend:', data);
        
        // Mapeamos o UserResponse para o nosso objeto wallet da tela
        this.wallet = {
          balance: data.balance,
          totalWon: data.totalWon,     
          totalLost: data.totalLost,
          totalPending: data.totalPending
        };
      }
    });

    // 2. Dispara a carga dos dados
    this.userService.loadUserProfile();
  }
}