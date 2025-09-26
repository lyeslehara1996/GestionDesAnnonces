import { Component, OnInit } from '@angular/core';
import { Annonce, AnnonceService } from 'src/services/annonce.service';

@Component({
  selector: 'app-annonce-list',
  templateUrl: './annonce-list.component.html',
  styleUrls: ['./annonce-list.component.scss']
})
export class AnnonceListComponent implements OnInit {
annonces :Annonce[] = [];
displayedColumns: string[] = ['titre', 'categorie', 'prix', 'auteur', 'actions'];

  constructor(private annonceService: AnnonceService) { }

  ngOnInit(): void {
  this.loadAnnonces();
console.log(this.annonces); 
}
  loadAnnonces(): void {

    this.annonceService.getAll().subscribe(data=>this.annonces = data);
    
  }

  deleteAnnonce(id:number){
    if(confirm('supprimer cette annonce?')){
      this.annonceService.delete(id).subscribe(()=>this.loadAnnonces());
    }
  }



}
