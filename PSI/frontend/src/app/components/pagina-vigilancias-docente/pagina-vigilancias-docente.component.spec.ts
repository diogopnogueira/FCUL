import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaVigilanciasDocenteComponent } from './pagina-vigilancias-docente.component';

describe('PaginaVigilanciasDocenteComponent', () => {
  let component: PaginaVigilanciasDocenteComponent;
  let fixture: ComponentFixture<PaginaVigilanciasDocenteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginaVigilanciasDocenteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaVigilanciasDocenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
