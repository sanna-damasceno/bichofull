import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterComponent } from '../../components/footer/footer'; 


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule, FooterComponent], 
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class HomeComponent {}