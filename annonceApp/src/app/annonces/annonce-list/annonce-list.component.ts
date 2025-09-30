import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Annonce, AnnonceService, Page } from 'src/services/annonce.service';
import { MatDialog } from '@angular/material/dialog';
import { AnnonceEditComponent } from '../annonce-edit/annonce-edit.component';
import { Router } from '@angular/router';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-annonce-list',
  templateUrl: './annonce-list.component.html',
  styleUrls: ['./annonce-list.component.scss']
})
export class AnnonceListComponent implements OnInit {
  annonces: Annonce[] = [];
  displayedColumns: string[] = [
    'title',
    'categorie',
    'prix',
    'auteur',
    'email',
    'telephone',
    'description',
    'actions'
  ];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  errorMessage: string | null = null;
  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;
  sort = 'dateCreation';
  direction = 'desc';

  filterForm!: FormGroup;
  categories = ['IMMOBILIER', 'AUTOMOBILE', 'EMPLOI', 'SERVICES', 'LOISIRS'];

  constructor(
    private annonceService: AnnonceService,
    private _dialog: MatDialog,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      title: [''],   // <-- correspond à ton entity backend
      prixMin: [''],
      prixMax: [''],
      categories: [[]]
    });
    this.loadAnnonces();
  }

  loadAnnonces(): void {
    const filters = {
      title: this.filterForm.value.title,
      prixMin: this.filterForm.value.prixMin,
      prixMax: this.filterForm.value.prixMax,
      categories: this.filterForm.value.categories
    };

    this.annonceService.searchAnnonces(
      this.pageIndex,
      this.pageSize,
      this.sort,
      this.direction,
      filters
    ).subscribe({
      next: (data: Page<Annonce>) => {
        this.annonces = data.content;
        this.totalElements = data.totalElements;
      },
      error: (err) => {
        this.errorMessage = err.error;
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadAnnonces();
  }

  applyFilters() {
    this.pageIndex = 0;
    this.loadAnnonces();
  }

  resetFilters() {
    this.filterForm.reset({
      title: '',
      prixMin: '',
      prixMax: '',
      categories: []
    });
    this.pageIndex = 0;
    this.loadAnnonces();
  }

  openEditAnnonceForm(annonces: Annonce) {
    const dialogRef = this._dialog.open(AnnonceEditComponent, {
      width: '600px',
      data: { annonces }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadAnnonces();
      }
    });
  }

  deleteAnnonce(id: number) {
    if (confirm('Supprimer cette annonce?')) {
      this.annonceService.delete(id).subscribe(() => this.loadAnnonces());
    }
  }
}
