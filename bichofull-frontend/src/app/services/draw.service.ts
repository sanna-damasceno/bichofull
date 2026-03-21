import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DrawResponse {
  id: number;
  drawDate: string;
  firstPrize: string;
  secondPrize: string;
  thirdPrize: string;
  fourthPrize: string;
  fifthPrize: string;
}

@Injectable({
  providedIn: 'root'
})
export class DrawService {

  private apiUrl = 'http://localhost:8080/api/draws';

  constructor(private http: HttpClient) {}

  runDraw(): Observable<DrawResponse> {
    return this.http.post<DrawResponse>(`${this.apiUrl}/run`, {});
  }
}