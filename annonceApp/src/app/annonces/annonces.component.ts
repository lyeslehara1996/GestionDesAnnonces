import { Component, OnInit } from '@angular/core';

import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
// import { KeycloakService } from 'src/services/keycloak.service';
@Component({
  selector: 'app-annonces',
  templateUrl: './annonces.component.html',
  styleUrls: ['./annonces.component.scss']
})
export class AnnoncesComponent implements OnInit {
user: { username?: string; firstName?: string; lastName?: string; email?: string } | null = null;

  constructor( private router:Router, private authService: AuthService) { }

  ngOnInit(): void {
      if (this.authService.isLoggedIn()) {
      this.user = this.authService.getUserProfile();
    }
  }

  isAdmin(): boolean {
  return this.authService.hasRole('admin');
}


  OnAddAnnonces(){

 this.router.navigateByUrl("/annonces/add");
  }

   logout() {
    this.authService.logout();
  }

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
}
