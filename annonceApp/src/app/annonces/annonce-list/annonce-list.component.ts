import { Component, OnInit } from '@angular/core';
import { Annonce, AnnonceService } from 'src/services/annonce.service';
import { MatDialog } from '@angular/material/dialog';
import { AnnonceEditComponent } from '../annonce-edit/annonce-edit.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-annonce-list',
  templateUrl: './annonce-list.component.html',
  styleUrls: ['./annonce-list.component.scss']
})
export class AnnonceListComponent implements OnInit {
annonces :Annonce[] = [];
displayedColumns: string[] = ['titre', 'categorie', 'prix', 'auteur','email','telephone','description', 'actions'];

  errorMessage :string|null = null;
  constructor(private annonceService: AnnonceService, private _dialog :MatDialog,private router:Router) { }

  ngOnInit(): void {
  this.loadAnnonces();
console.log(this.annonces); 
}
  loadAnnonces(): void {

   this.annonceService.getAll().subscribe({
      next:(data) =>{
        this.annonces=data;
        console.log(data);
        
      },
      error:(err)=>{
        this.errorMessage = err.error;
      }
    })
  
  }

  openEditAnnonceForm(annonces:Annonce){
   const dialogRef = this._dialog.open(AnnonceEditComponent, {
    width: '600px',
    data: { annonces }
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.loadAnnonces(); // recharge la liste si une modif a eu lieu
    }
  });
  }
  deleteAnnonce(id:number){
    if(confirm('supprimer cette annonce?')){
      this.annonceService.delete(id).subscribe(()=>this.loadAnnonces());
    }
  }



}
