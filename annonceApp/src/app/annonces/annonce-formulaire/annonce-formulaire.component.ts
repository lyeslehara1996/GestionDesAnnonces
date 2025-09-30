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
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      description: [''],
      prix: [null, [Validators.required, Validators.min(1)]],
      categorie: ['', Validators.required],
      auteur: ['', Validators.required],
      email: ['', [Validators.email]],
      telephone: [''],
      active: [true]
    });

  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    const annonce: Annonce = this.form.value;
console.log(annonce);
  
      this.annonceService.create(annonce).subscribe(() => {
        alert('Annonce créée avec succès');
        console.log(annonce);
        this.router.navigate(['/']);
      });
    }
  }
