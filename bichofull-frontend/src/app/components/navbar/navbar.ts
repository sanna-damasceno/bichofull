import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; 
import { UserService } from '../../services/user.service'; 

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class NavbarComponent implements OnInit {

  user: any = { name: '', balance: 0 };

  constructor(private userService: UserService) {}

  ngOnInit() {
    
    this.userService.loadUserProfile();

    this.userService.user$.subscribe(user => {
      if (user) {
        this.user = user;
      }
    });
  }
}