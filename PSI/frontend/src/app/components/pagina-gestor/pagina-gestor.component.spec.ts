import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaGestorComponent } from './pagina-gestor.component';

describe('PaginaGestorComponent', () => {
  let component: PaginaGestorComponent;
  let fixture: ComponentFixture<PaginaGestorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginaGestorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaGestorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
