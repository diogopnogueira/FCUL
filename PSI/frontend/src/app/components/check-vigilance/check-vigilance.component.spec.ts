import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckVigilanceComponent } from './check-vigilance.component';

describe('CheckVigilanceComponent', () => {
  let component: CheckVigilanceComponent;
  let fixture: ComponentFixture<CheckVigilanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckVigilanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckVigilanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
