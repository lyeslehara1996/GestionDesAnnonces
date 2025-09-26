import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnonceFormulaireComponent } from './annonce-formulaire.component';

describe('AnnonceFormulaireComponent', () => {
  let component: AnnonceFormulaireComponent;
  let fixture: ComponentFixture<AnnonceFormulaireComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnnonceFormulaireComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnonceFormulaireComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
