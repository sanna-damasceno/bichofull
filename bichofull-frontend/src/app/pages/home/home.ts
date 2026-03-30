import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterComponent } from '../../components/footer/footer'; // ajuste o caminho

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule, FooterComponent], // Adicionado aqui
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class HomeComponent {}