import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaDocenteComponent } from './pagina-docente.component';

describe('PaginaDocenteComponent', () => {
  let component: PaginaDocenteComponent;
  let fixture: ComponentFixture<PaginaDocenteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginaDocenteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaDocenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
