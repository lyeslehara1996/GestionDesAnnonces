import { Injectable } from '@angular/core';
import Keycloak, { KeycloakInstance, KeycloakTokenParsed } from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private keycloak?: KeycloakInstance;
  private initialized = false;

  async init(): Promise<void> {
    this.keycloak = new Keycloak({
      url: 'http://localhost:8080',
      realm: 'SCPI_InvestPLUS',
      clientId: 'SCPI-client'
    });

    try {
      const authenticated = await this.keycloak.init({
        onLoad: 'login-required',
        checkLoginIframe: false,
        redirectUri: window.location.origin
      });

      this.initialized = true;

      if (authenticated) {
        console.log(' Utilisateur authentifié :', this.keycloak.tokenParsed);
      } else {
        console.warn(' Utilisateur non authentifié');
      }
    } catch (error) {
      console.error('Erreur Keycloak init :', error);
      this.initialized = false;
    }
  }

  /** 👉 Nouvelle méthode publique pour attendre l’initialisation */
  async waitUntilReady(): Promise<void> {
    if (this.initialized) return;
    let retries = 10;
    while (!this.initialized && retries-- > 0) {
      await new Promise(res => setTimeout(res, 200));
    }
    if (!this.initialized) throw new Error('Keycloak non initialisé après attente.');
  }

  private ensureInitialized(): void {
    if (!this.keycloak || !this.initialized) {
      throw new Error('Keycloak n’est pas encore initialisé.');
    }
  }

  isLoggedIn(): boolean {
    this.ensureInitialized();
    return !!this.keycloak?.authenticated;
  }

  login(): void {
    this.ensureInitialized();
    this.keycloak!.login({ redirectUri: window.location.origin });
  }

  logout(): void {
    this.ensureInitialized();
    this.keycloak!.logout({ redirectUri: window.location.origin });
  }

  async getToken(): Promise<string> {
    this.ensureInitialized();
    try {
      await this.keycloak!.updateToken(30);
      return this.keycloak!.token!;
    } catch (err) {
      console.error('Erreur lors de la récupération du token :', err);
      this.login();
      throw err;
    }
  }

  getUserProfile(): { username?: string; firstName?: string; lastName?: string; email?: string } {
    this.ensureInitialized();
    const tokenParsed = this.keycloak!.tokenParsed as KeycloakTokenParsed & {
      given_name?: string;
      family_name?: string;
      email?: string;
    };
    return {
      username: tokenParsed?.['preferred_username'],
      firstName: tokenParsed?.given_name,
      lastName: tokenParsed?.family_name,
      email: tokenParsed?.email
    };
  }

  hasRole(role: string): boolean {
    this.ensureInitialized();
    const realmRoles = this.keycloak!.realmAccess?.roles || [];
    const clientId = this.keycloak!.clientId ?? 'SCPI-client';
    const resourceRoles = this.keycloak!.resourceAccess?.[clientId]?.roles || [];
    return [...realmRoles, ...resourceRoles].includes(role);
  }
}
