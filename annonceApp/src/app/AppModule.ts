import { HttpClientModule,HTTP_INTERCEPTORS} from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AnnonceDetailComponent } from './annonces/annonce-detail/annonce-detail.component';
import { AnnonceFormulaireComponent } from './annonces/annonce-formulaire/annonce-formulaire.component';
import { AnnonceListComponent } from './annonces/annonce-list/annonce-list.component';
import { AnnonceRechercheComponent } from './annonces/annonce-recherche/annonce-recherche.component';
import { AnnoncesComponent } from './annonces/annonces.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AnnonceEditComponent } from './annonces/annonce-edit/annonce-edit.component';
import { KeycloakService } from '../services/keycloak.service';
import { AuthInterceptor } from 'src/Intercepteur/auth.interceptor';

export function initializeKeycloak(keycloak: KeycloakService): () => Promise<void> {
  return () => keycloak.init();
}


@NgModule({
  declarations: [
    AppComponent,
    AnnoncesComponent,
    AnnonceListComponent,
    AnnonceFormulaireComponent,
    AnnonceDetailComponent,
    AnnonceRechercheComponent,
    AnnonceEditComponent, 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,

    // Angular Material
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
  ],
  providers: [  
      KeycloakService,
    KeycloakService,
  {
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloak,
    multi: true,
    deps: [KeycloakService]
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }

   ],
  bootstrap: [AppComponent],
})
export class AppModule {}
