import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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

  private readonly API_URL = 'http://localhost:8080/api/users/me';

  user = signal<UserResponse | null>(null);

  constructor(private http: HttpClient) {}

  loadUserProfile(): void {
    this.http.get<UserResponse>(this.API_URL)
      .subscribe(user => {
        this.user.set(user);
      });
  }

  updateLocalBalance(newBalance: number): void {
    const currentUser = this.user();

    if (currentUser) {
      this.user.set({
        ...currentUser,
        balance: newBalance
      });
    }
  }
}