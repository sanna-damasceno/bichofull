import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BetResponse, BetService } from '../../services/bet.service';

@Component({
  selector: 'app-bet-history',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './bet-history.html',
  styleUrl: './bet-history.css',
})
export class BetHistoryComponent implements OnInit {

  bets = signal<BetResponse[]>([]);

  stats = computed(() => {
    const bets = this.bets();

    const total = bets.length;
    const won = bets.filter(b => b.status === 'WON');
    const lost = bets.filter(b => b.status === 'LOST');

    return {
      totalBets: total,
      totalWon: won.reduce((sum, b) => sum + (b.prize || 0), 0),
      totalLost: lost.reduce((sum, b) => sum + b.amount, 0),
      winRate: total > 0 ? (won.length / total) * 100 : 0
    };
  });

  constructor(private betService: BetService) {}

  ngOnInit(): void {
    this.fetchBetHistory();
  }

  fetchBetHistory(): void {
    this.betService.getMyBets().subscribe({
      next: (dados: any) => {

        let lista: BetResponse[] = [];

        if (Array.isArray(dados)) {
          lista = dados;
        } else if (dados?.content) {
          lista = dados.content;
        } else if (dados?.bets) {
          lista = dados.bets;
        }

        this.bets.set(lista);
      },
      error: (err) => {
        console.error('Erro:', err);
      }
    });
  }

  translateStatus(status: string): string {
    const labels: any = { WON: 'Ganhou', LOST: 'Perdeu', PENDING: 'Pendente' };
    return labels[status] || status;
  }

  getBadgeClass(status: string): string {
    switch (status) {
      case 'WON': return 'badge-ganhou';
      case 'LOST': return 'badge-perdeu';
      case 'PENDING': return 'badge-pendente';
      default: return '';
    }
  }
  translateType(type: string): string {
    const types: any = {
      GROUP: 'Grupo',
      TEN: 'Dezena',
      THOUSAND: 'Milhar'
    };
    return types[type] || type;
  }
}