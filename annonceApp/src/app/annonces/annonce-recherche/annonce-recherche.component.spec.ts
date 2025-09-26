import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnonceRechercheComponent } from './annonce-recherche.component';

describe('AnnonceRechercheComponent', () => {
  let component: AnnonceRechercheComponent;
  let fixture: ComponentFixture<AnnonceRechercheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnnonceRechercheComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnonceRechercheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
