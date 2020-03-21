import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UserAgreementPage } from './user-agreement.page';

describe('UserAgreementPage', () => {
  let component: UserAgreementPage;
  let fixture: ComponentFixture<UserAgreementPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserAgreementPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UserAgreementPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
