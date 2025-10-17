import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AnnonceListComponent } from './annonces/annonce-list/annonce-list.component';
import { AnnonceFormulaireComponent } from './annonces/annonce-formulaire/annonce-formulaire.component';
import { AnnonceDetailComponent } from './annonces/annonce-detail/annonce-detail.component';
import { AuthGuard } from '../guard/auth.guard';
import { ForbiddenComponent } from './forbidden/forbidden.component';
const routes: Routes = [
 { path: '', component: AnnonceListComponent },
{ path: 'forbidden', component: ForbiddenComponent },

  { path: 'annonces', component: AnnonceListComponent, canActivate: [AuthGuard], data: { roles: ['user'] } },
  { path: 'annonces/add', component: AnnonceFormulaireComponent, canActivate: [AuthGuard], data: { roles: ['admin'] } },
  { path: 'annonces/:id', component: AnnonceDetailComponent, canActivate: [AuthGuard], data: { roles: ['user'] } },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [
  
  ]
})
export class AppRoutingModule { }
