import { Injectable } from '@angular/core';
import { OAuthService, AuthConfig } from 'angular-oauth2-oidc';
import { authConfig } from '../config/auth.config';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private oauthService: OAuthService) {
    this.oauthService.configure(authConfig);
    this.oauthService.setupAutomaticSilentRefresh();
  }

 async init(): Promise<void> {
  await this.oauthService.loadDiscoveryDocumentAndTryLogin();

  if (!this.oauthService.hasValidAccessToken()) {
    await this.login();
  }

 }

private decodeJwt(token: string): any {
  const parts = token.split('.');
  return JSON.parse(atob(parts[1]));
}


  async login(): Promise<void> {
    await this.oauthService.initLoginFlow();
  }

  logout(): void {
    this.oauthService.logOut();
  }

  get accessToken(): string | null {
    return this.oauthService.getAccessToken();
  }

  get identityClaims(): any {
    return this.oauthService.getIdentityClaims();
  }

  isLoggedIn(): boolean {
    return this.oauthService.hasValidAccessToken();
  }

hasRole(role: string): boolean {
  const token = this.oauthService.getAccessToken();
  if (!token) return false;

  const claims: any = this.decodeJwt(token);
  if (!claims) return false;

  const realmRoles = claims.realm_access?.roles || [];
  const clientRoles = claims.resource_access?.['SCPI-client']?.roles || [];

  const allRoles = [...realmRoles, ...clientRoles];

  return allRoles.includes(role);
}

 

  getUserProfile(): { username?: string; firstName?: string; lastName?: string; email?: string } {
    const claims: any = this.oauthService.getIdentityClaims();
    return {
      username: claims?.preferred_username,
      firstName: claims?.given_name,
      lastName: claims?.family_name,
      email: claims?.email,
    };
  }
}
