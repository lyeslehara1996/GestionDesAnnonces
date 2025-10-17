// import { Injectable } from '@angular/core';
// import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
// import { Observable, from } from 'rxjs';
// import { switchMap } from 'rxjs/operators';
// import { KeycloakService } from '../services/keycloak.service';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   constructor(private keycloakService: KeycloakService) {}

//   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
//     return from(this.keycloakService.getToken()).pipe(
//       switchMap(token => {
//         const authReq = req.clone({
//           setHeaders: { Authorization: `Bearer ${token}` }
//         });
//         return next.handle(authReq);
//       })
//     );
//   }
// }


import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.accessToken;

    if (token) {
      const authReq = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
      return next.handle(authReq);
    }

    return next.handle(req);
  }
}


