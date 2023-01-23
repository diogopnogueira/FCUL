import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaVigilanciasComponent } from './pagina-vigilancias.component';

describe('PaginaVigilanciasComponent', () => {
  let component: PaginaVigilanciasComponent;
  let fixture: ComponentFixture<PaginaVigilanciasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginaVigilanciasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaVigilanciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
