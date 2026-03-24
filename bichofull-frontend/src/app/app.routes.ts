import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { DashboardComponent  } from './pages/dashboard/dashboard';
import { AdminDrawComponent  } from './pages/admin-draw/admin-draw';

import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminDrawComponent, canActivate: [adminGuard] },

  {
    path: 'bet-history',
    loadComponent: () =>
      import('./pages/bet-history/bet-history')
        .then(m => m.BetHistoryComponent),
        canActivate: [authGuard]
  },

  {
    path: 'draws',
    loadComponent: () =>
      import('./pages/draws/draws')
        .then(m => m.DrawsComponent),
        canActivate: [authGuard]
  },

  { path: 'admin', component: AdminDrawComponent, 
    canActivate: [adminGuard] },

  { path: '**', redirectTo: 'login' }
];