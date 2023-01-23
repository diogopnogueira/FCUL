import { TestBed } from '@angular/core/testing';

import { TrocasService } from './trocas.service';

describe('TrocasService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TrocasService = TestBed.get(TrocasService);
    expect(service).toBeTruthy();
  });
});
