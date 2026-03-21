import { OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BetResponse, BetService } from '../../services/bet.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-bet-history',
  imports: [CommonModule, RouterModule],
  templateUrl: './bet-history.html',
  styleUrl: './bet-history.css',
})
export class BetHistoryComponent implements OnInit, OnDestroy {
  bets: BetResponse[] = [];

  sub!: Subscription;

  stats = {
    totalBets: 0,
    winRate: 0,
    totalWon: 0,
    totalLost: 0
  };

  constructor(private betService: BetService,
              private router: Router,
              private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.fetchBetHistory();
    this.sub = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.fetchBetHistory();
      }
    });
  }

  updateStats() {
    const total = this.bets.length;
    const won = this.bets.filter(b => b.status === 'WON');
    const lost = this.bets.filter(b => b.status === 'LOST');

    this.stats.totalBets = total;
    this.stats.totalWon = won.reduce((sum, b) => sum + (b.prize || 0), 0);
    this.stats.totalLost = lost.reduce((sum, b) => sum + b.amount, 0);
    this.stats.winRate = total > 0 ? (won.length / total) * 100 : 0;
  }

  fetchBetHistory(): void {
    this.betService.getMyBets().subscribe({
      next: (dados: any) => {
        if (Array.isArray(dados)) {
          this.bets = dados;
        } else if (dados?.content) {
          this.bets = dados.content;
        } else if (dados?.bets) {
          this.bets = dados.bets;
        } else {
          console.error('Formato inesperado:', dados);
          this.bets = [];
        }

        this.updateStats();
        this.cdr.detectChanges();

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

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

}
