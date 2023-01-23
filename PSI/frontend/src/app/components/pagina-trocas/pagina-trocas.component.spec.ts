import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaTrocasComponent } from './pagina-trocas.component';

describe('PaginaTrocasComponent', () => {
  let component: PaginaTrocasComponent;
  let fixture: ComponentFixture<PaginaTrocasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginaTrocasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaTrocasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
