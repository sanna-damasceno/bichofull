import { Component, OnDestroy, OnInit } from '@angular/core';
import { DrawService, DrawResponse } from '../../services/draw.service';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-draw',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-draw.html',
  styleUrls: ['./admin-draw.css'],
})
export class AdminDrawComponent implements OnInit, OnDestroy {

  result: DrawResponse | null = null;
  loading = false;
  success = false;

    manualDraw = {
    firstPrize: '',
    secondPrize: '',
    thirdPrize: '',
    fourthPrize: '',
    fifthPrize: ''
  };

  sub!: Subscription;

  constructor(
    private drawService: DrawService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.sub = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.resetState();
      }
    });
  }

  runDraw() {
    this.loading = true;
    this.success = false;

    this.drawService.runDraw().subscribe({
      next: (res) => {
        console.log("CHEGOU RESPOSTA", res);

        this.result = res;
        this.success = true;
        this.loading = false;

        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        alert('Erro ao executar sorteio');
      }
    });
  }

  resetState() {
    this.result = null;
    this.success = false;
    this.loading = false;
  }

  createManualDraw() {
    this.loading = true;
    this.success = false;

    this.drawService.createDraw(this.manualDraw).subscribe({
      next: (res) => {
        this.result = res;
        this.success = true;
        this.loading = false;

        this.resetManualForm();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        alert('Erro ao criar sorteio manual');
      }
    });
  }

  resetManualForm() {
    this.manualDraw = {
      firstPrize: '',
      secondPrize: '',
      thirdPrize: '',
      fourthPrize: '',
      fifthPrize: ''
    };
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }


}