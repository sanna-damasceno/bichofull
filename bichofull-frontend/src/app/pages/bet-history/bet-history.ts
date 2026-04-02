import { Component, OnInit, signal, computed, inject } from '@angular/core'; // Adicionado inject
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BetResponse, BetService } from '../../services/bet.service';
import { AnimalService } from '../../services/animal.service'; // Adicionada importação

@Component({
  selector: 'app-bet-history',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './bet-history.html',
  styleUrl: './bet-history.css',
})
export class BetHistoryComponent implements OnInit {
  // Serviços injetados de forma moderna
  private betService = inject(BetService);
  private animalService = inject(AnimalService);

  // Controle de paginação
  currentPage = signal(1);
  pageSize = 5;

  bets = signal<BetResponse[]>([]);
  private animals: any[] = [];

  // Stats calculados automaticamente usando Signals
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

  paginatedBets = computed(() => {
    const startIndex = (this.currentPage() - 1) * this.pageSize;
    return this.bets().slice(startIndex, startIndex + this.pageSize);
  });

  totalPages = computed(() => Math.ceil(this.bets().length / this.pageSize));

  // Gera o array de números das páginas [1, 2, 3...]
  pagesArray = computed(() => Array.from({ length: this.totalPages() }, (_, i) => i + 1));

  changePage(page: number) {
    if (page >= 1 && page <= this.totalPages()) {
      this.currentPage.set(page);
    }
  }


  ngOnInit(): void {
    // 1. Primeiro carregamos os animais para que o mapeamento de nomes funcione
    this.animalService.getAnimals().subscribe({
      next: (data) => {
        this.animals = data;
        // 2. Só depois buscamos o histórico
        this.fetchBetHistory();
      },
      error: (err) => console.error('Erro ao carregar animais:', err)
    });
  }

  // --- LÓGICA DE IDENTIFICAÇÃO DOS BICHOS ---

  getAnimalName(bet: any): string {
    if (!bet.chosenNumber || this.animals.length === 0) return '...';

    let found: any = null;

    if (bet.type === 'GROUP') {
      // No GRUPO, o chosenNumber é o número do grupo (1 a 25)
      const groupNum = parseInt(bet.chosenNumber, 10);
      found = this.animals.find((a) => a.groupNumber === groupNum);
    } else {
      // Para TEN (Dezena) ou THOUSAND (Milhar), usa os últimos 2 dígitos
      const lastTwoDigits = bet.chosenNumber.slice(-2).padStart(2, '0');
      found = this.animals.find((a) => a.dezenas.includes(lastTwoDigits));
    }
    
    return found ? found.name : 'Outro';
  }

  getAnimalPath(bet: any): string {
    if (!bet.chosenNumber || this.animals.length === 0) return 'default';

    let found: any = null;

    if (bet.type === 'GROUP') {
      const groupNum = parseInt(bet.chosenNumber, 10);
      found = this.animals.find((a) => a.groupNumber === groupNum);
    } else {
      const lastTwoDigits = bet.chosenNumber.slice(-2).padStart(2, '0');
      found = this.animals.find((a) => a.dezenas.includes(lastTwoDigits));
    }

    if (!found) return 'default';

    // Retorna o número do grupo formatado (ex: "01") para o caminho da imagem
    return found.groupNumber.toString().padStart(2, '0');
  }

  // --- BUSCA DE DADOS ---

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
        this.currentPage.set(1);
      },
      error: (err) => console.error('Erro ao buscar histórico:', err)
    });
  }

  // --- TRADUÇÕES E ESTILOS ---

  translateStatus(status: string): string {
    const labels: any = { WON: 'Ganhou', LOST: 'Perdeu', PENDING: 'Pendente' };
    return labels[status] || status;
  }

  getBadgeClass(status: string): string {
    switch (status) {
      case 'WON': return 'status-won';
      case 'LOST': return 'status-lost';
      case 'PENDING': return 'status-pending';
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