import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaExamesComponent } from './lista-exames.component';

describe('ListaExamesComponent', () => {
  let component: ListaExamesComponent;
  let fixture: ComponentFixture<ListaExamesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaExamesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaExamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
