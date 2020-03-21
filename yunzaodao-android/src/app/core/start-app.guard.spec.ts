import { TestBed, async, inject } from '@angular/core/testing';

import { StartAppGuard } from './start-app.guard';

describe('StartAppGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StartAppGuard]
    });
  });

  it('should ...', inject([StartAppGuard], (guard: StartAppGuard) => {
    expect(guard).toBeTruthy();
  }));
});
