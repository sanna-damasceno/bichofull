import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WalletDTO {
  balance: number;
  totalWon: number;
  totalLost: number;
  totalPending: number;
}

@Injectable({ providedIn: 'root' })
export class WalletService {
  private readonly API_URL = 'http://localhost:8080/api/users/me';

  constructor(private http: HttpClient) {}

  getWalletInfo(): Observable<WalletDTO> {
    return this.http.get<WalletDTO>(this.API_URL);
  }
}