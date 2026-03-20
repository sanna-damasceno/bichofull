import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  balance: number;
  totalWon: number;     
  totalLost: number;    
  totalPending: number;
}

@Injectable({ providedIn: 'root' })
export class UserService {
  private readonly API_URL = 'http://localhost:8080/api/users/me'; // Ajuste para seu endpoint de perfil
  
  // O BehaviorSubject guarda o último valor emitido
  private userSubject = new BehaviorSubject<UserResponse | null>(null);
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Busca os dados completos do usuário e avisa quem estiver "ouvindo"
  loadUserProfile(): void {
    this.http.get<UserResponse>(this.API_URL).subscribe(user => {
      this.userSubject.next(user);
    });
  }

  // Método manual para atualizar o saldo localmente após uma aposta
  updateLocalBalance(newBalance: number): void {
    const currentUser = this.userSubject.value;
    if (currentUser) {
      this.userSubject.next({ ...currentUser, balance: newBalance });
    }
  }
}