// src/app/components/hero-section/hero-section.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // IMPORTANTE

@Component({
  selector: 'app-hero-section',
  standalone: true,
  imports: [CommonModule, RouterModule], // ADICIONE AQUI
  templateUrl: './hero-section.html',
  styleUrl: './hero-section.css'
})
export class HeroSectionComponent {}