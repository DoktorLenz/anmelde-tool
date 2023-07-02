import { TestBed } from '@angular/core/testing';

import { NamiMembersService } from './nami-members.service';

describe('NamiMembersService', () => {
  let service: NamiMembersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NamiMembersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
