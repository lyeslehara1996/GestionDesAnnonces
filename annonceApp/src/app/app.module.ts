import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';

import { HttpClientModule } from  '@angular/common/http';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AnnoncesComponent } from './annonces/annonces.component';
import { AnnonceListComponent } from './annonces/annonce-list/annonce-list.component';
import { AnnonceFormulaireComponent } from './annonces/annonce-formulaire/annonce-formulaire.component';
import { AnnonceDetailComponent } from './annonces/annonce-detail/annonce-detail.component';
import { AnnonceRechercheComponent } from './annonces/annonce-recherche/annonce-recherche.component';

import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { ReactiveFormsModule } from '@angular/forms';
@NgModule({
  declarations: [
    AppComponent,
    AnnoncesComponent,
    AnnonceListComponent,
    AnnonceFormulaireComponent,
    AnnonceDetailComponent,
    AnnonceRechercheComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
     ReactiveFormsModule,
    HttpClientModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
