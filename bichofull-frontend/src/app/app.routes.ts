import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { DashboardComponent  } from './pages/dashboard/dashboard';
import { AdminDrawComponent  } from './pages/admin-draw/admin-draw';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'admin', component: AdminDrawComponent },

  {
    path: 'bet-history',
    loadComponent: () =>
      import('./pages/bet-history/bet-history')
        .then(m => m.BetHistoryComponent)
  },

  {
    path: 'draws',
    loadComponent: () =>
      import('./pages/draws/draws')
        .then(m => m.DrawsComponent)
  },

  { path: '**', redirectTo: 'login' }
];