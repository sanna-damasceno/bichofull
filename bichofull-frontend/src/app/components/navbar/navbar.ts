import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router'; 
import { UserService } from '../../services/user.service'; 

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class NavbarComponent implements OnInit {

  private userService = inject(UserService);
  private router = inject(Router);

  user = this.userService.user;

  ngOnInit(){
    this.userService.loadUserProfile();
  }

  logout() {
    localStorage.clear();

    this.router.navigate(['/login']).then(() => {
      window.location.reload();
    });
  }
}