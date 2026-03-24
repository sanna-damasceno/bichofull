import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router'; 
import { UserService } from '../../services/user.service'; 
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class NavbarComponent implements OnInit, OnDestroy {

  user: any = { name: '', balance: 0 };
  sub!: Subscription;

  constructor(
    private userService: UserService,
    private router: Router 
  ) {}

  ngOnInit() {
    this.userService.loadUserProfile();

    this.sub = this.userService.user$.subscribe(user => {
      if (user) {
        this.user = user;
      }
    });
  }

  logout() {

    localStorage.clear();

    this.router.navigate(['/login']);
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }
}