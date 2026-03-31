import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
import { DashboardComponent  } from './pages/dashboard/dashboard';
import { AdminDrawComponent  } from './pages/admin-draw/admin-draw';
import { MainLayoutComponent } from './layouts/main-layout/main-layout';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout';

import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { HowItWorksComponent } from './components/how-it-works/how-it-works';

export const routes: Routes = [
  // 1. Rota Raiz
  { path: '', component: HomeComponent },

  // 2. Rotas de Autenticação
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  
  // 3. Rotas Internas com Layout Principal e Proteção
  {
    path: '',
    component: MainLayoutComponent,
    children: [
        { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },

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

        { path: 'how-it-works', component: HowItWorksComponent },

    ]
  },

  // 4. Rota Administrativa
  { path: 'admin', component: AdminDrawComponent, 
    canActivate: [adminGuard] },

  // 5. Wildcard (Se a rota não existir, volta para a Home)
  { path: '**', redirectTo: 'home' }
];