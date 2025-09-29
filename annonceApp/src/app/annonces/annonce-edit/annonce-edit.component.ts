import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Annonce, AnnonceService } from 'src/services/annonce.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-annonce-edit',
  templateUrl: './annonce-edit.component.html',
  styleUrls: ['./annonce-edit.component.scss']
})
export class AnnonceEditComponent implements OnInit {

  form!: FormGroup;
  annonceId?: number;
  categories = ['IMMOBILIER', 'AUTOMOBILE', 'EMPLOI', 'SERVICES', 'LOISIRS'];

  constructor(
    private fb: FormBuilder,
    private annonceService: AnnonceService,
    private router: Router,
    private dialogRef: MatDialogRef<AnnonceEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number }   // ✅ bonne syntaxe
  ) {}

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

    // récupérer l'id depuis le dialog
    this.annonceId = this.data?.id;
    console.log(this.data)

    if (this.annonceId) {
      this.annonceService.getById(this.annonceId).subscribe(annonce => {
        this.form.patchValue(annonce);
      });
    }
  }

  onSubmit() {
    if (this.form.invalid) return;

    const annonce: Annonce = this.form.value;

    if (this.annonceId) {
      this.annonceService.update(this.annonceId, annonce).subscribe(() => {
        alert('Annonce mise à jour avec succès');
        this.dialogRef.close(true); // ferme la modal et renvoie un signal
      });
    }
  }
}
