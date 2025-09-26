import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Annonce, AnnonceService } from 'src/services/annonce.service';

@Component({
  selector: 'app-annonce-formulaire',
  templateUrl: './annonce-formulaire.component.html',
  styleUrls: ['./annonce-formulaire.component.scss']
})
export class AnnonceFormulaireComponent implements OnInit {

   form!: FormGroup;
  annonceId?: number;
  categories = ['IMMOBILIER', 'AUTOMOBILE', 'EMPLOI', 'SERVICES', 'LOISIRS'];

  constructor( private fb: FormBuilder,
    private annonceService: AnnonceService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {

 this.form = this.fb.group({
      titre: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      description: [''],
      prix: [null, [Validators.required, Validators.min(1)]],
      categorie: ['', Validators.required],
      auteur: ['', Validators.required],
      email: ['', [Validators.email]],
      telephone: [''],
      active: [true]
    });
  
    // Vérifier si on est en mode édition
    this.annonceId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.annonceId) {
      this.annonceService.getById(this.annonceId).subscribe(annonce => {
        this.form.patchValue(annonce);
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    const annonce: Annonce = this.form.value;

    if (this.annonceId) {
      this.annonceService.update(this.annonceId, annonce).subscribe(() => {
        alert('Annonce mise à jour avec succès');
        this.router.navigate(['/']);
      });
    } else {
      this.annonceService.create(annonce).subscribe(() => {
        alert('Annonce créée avec succès');
        this.router.navigate(['/']);
      });
    }
  }
}
