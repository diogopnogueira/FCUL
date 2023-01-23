import { TestBed } from '@angular/core/testing';

import { ExameService } from './exame.service';

describe('ExameService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExameService = TestBed.get(ExameService);
    expect(service).toBeTruthy();
  });
});
