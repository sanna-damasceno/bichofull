import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BetResponse, BetService } from '../../services/bet.service';

@Component({
  selector: 'app-bet-history',
  imports: [CommonModule, RouterModule],
  templateUrl: './bet-history.html',
  styleUrl: './bet-history.css',
})
export class BetHistoryComponent implements OnInit {
  bets: BetResponse[] = [];

  stats = {
    totalBets: 0,
    winRate: 0,
    totalWon: 0,
    totalLost: 0
  };

  constructor(private betService: BetService) {}

  ngOnInit(): void {
    this.fetchBetHistory();
  }

  fetchBetHistory(): void {
    this.betService.getMyBets().subscribe({
      next: (dados: BetResponse[]) => {
        this.bets = dados;
        console.log('Dados recebidos do Java:', dados);
      },
      error: (err) => {
        console.error('Erro de conexão com o Backend:', err);
      }
    });
  }

  translateStatus(status: string): string {
    const labels: any = { 'WON': 'Ganhou', 'LOST': 'Perdeu', 'PENDING': 'Pendente' };
    return labels[status] || status;
  }

  translateType(type: string): string {
    const types: any = { 'GROUP': 'Grupo', 'TEN': 'Dezena', 'THOUSAND': 'Milhar' };
    return types[type] || type;
  }

  getBadgeClass(status: string): string {
    switch (status) {
      case 'WON': return 'badge-ganhou'; 
      case 'LOST': return 'badge-perdeu';
      case 'PENDING': return 'badge-pendente';
      default: return '';
    }
  }

}
