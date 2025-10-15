import { Component, OnInit } from '@angular/core';

import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { KeycloakService } from 'src/services/keycloak.service';
@Component({
  selector: 'app-annonces',
  templateUrl: './annonces.component.html',
  styleUrls: ['./annonces.component.scss']
})
export class AnnoncesComponent implements OnInit {
user: { username?: string; firstName?: string; lastName?: string; email?: string } | null = null;

  constructor( private router:Router, private keycloakService: KeycloakService) { }

  ngOnInit(): void {
      if (this.keycloakService.isLoggedIn()) {
      this.user = this.keycloakService.getUserProfile();
    }
  }

  isAdmin(): boolean {
  return this.keycloakService.hasRole('admin');
}


  OnAddAnnonces(){

 this.router.navigateByUrl("/annonces/add");
  }

   logout() {
    this.keycloakService.logout();
  }

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }
}
