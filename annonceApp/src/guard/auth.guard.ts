import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { KeycloakService } from '../services/keycloak.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private keycloakService: KeycloakService, private router: Router) {}

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {
    try {
      await this.keycloakService.waitUntilReady();

      if (!this.keycloakService.isLoggedIn()) {
        console.warn('Utilisateur non connecté → redirection vers Keycloak');
        this.keycloakService.login();
        return false;
      }

      const requiredRoles = route.data['roles'] as string[] | undefined;

      if (requiredRoles?.length) {
        const hasAccess = requiredRoles.some(role => this.keycloakService.hasRole(role));
        if (!hasAccess) {
          console.warn('Accès refusé — rôles requis :', requiredRoles);
          return this.router.parseUrl('/forbidden');
        }
      }

      return true;
    } catch (err) {
      console.error('Erreur AuthGuard :', err);
      this.keycloakService.login();
      return false;
    }
  }
}
