import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaVigilanciasComponent } from './lista-vigilancias.component';

describe('ListaVigilanciasComponent', () => {
  let component: ListaVigilanciasComponent;
  let fixture: ComponentFixture<ListaVigilanciasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaVigilanciasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaVigilanciasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
