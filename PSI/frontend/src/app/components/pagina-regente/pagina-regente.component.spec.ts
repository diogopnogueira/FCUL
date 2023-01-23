import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaRegenteComponent } from './pagina-regente.component';

describe('PaginaRegenteComponent', () => {
  let component: PaginaRegenteComponent;
  let fixture: ComponentFixture<PaginaRegenteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaginaRegenteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaginaRegenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
