import { TestBed } from '@angular/core/testing';

import { AuthenticationCodeService } from './authentication-code.service';

describe('AuthenticationCodeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthenticationCodeService = TestBed.get(AuthenticationCodeService);
    expect(service).toBeTruthy();
  });
});
