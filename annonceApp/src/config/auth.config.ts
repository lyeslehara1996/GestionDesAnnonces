import { AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'http://localhost:8080/realms/SCPI_InvestPLUS',
  redirectUri: window.location.origin,
  clientId: 'SCPI-client',
  responseType: 'code',
  logoutUrl:"http://localhost:8080/realms/SCPI_InvestPLUS/protocol/openid-connect/logout",
  scope: 'openid profile email offline_access',
    timeoutFactor: 0.75,
  showDebugInformation: true,
  disableAtHashCheck: true,
};
