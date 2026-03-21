import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Alinhado com o seu BetRequestDTO do Java
export interface BetRequest {
  type: 'GROUP' | 'TEN' | 'THOUSAND'; 
  chosenNumber: string;
  amount: number;
}

// Alinhado com o seu BetResponseDTO do Java
export interface BetResponse {
  id: number;
  type: string;
  chosenNumber: string;
  amount: number;
  status: string;
  prize: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class BetService {
  private readonly API_URL = 'http://localhost:8080/api/bets';

  constructor(private http: HttpClient) {}

  placeBet(betData: BetRequest): Observable<BetResponse> {
    return this.http.post<BetResponse>(this.API_URL, betData);
  }

  getMyBets(): Observable<BetResponse[]> {
    return this.http.get<BetResponse[]>(`${this.API_URL}/my-bets`);
  }
}