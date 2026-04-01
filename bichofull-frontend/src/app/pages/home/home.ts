// src/app/pages/home/home.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// Importações dos novos componentes globais
import { HeroSectionComponent } from '../../components/hero-section/hero-section';
import { HowItWorksComponent } from '../../components/how-it-works/how-it-works';
import { AboutSectionComponent } from '../../components/about-section/about-section';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    HeroSectionComponent,
    HowItWorksComponent,
    AboutSectionComponent,
  ],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent {}