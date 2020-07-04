import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SigninInfoPage } from './signin-info.page';

describe('SigninInfoPage', () => {
  let component: SigninInfoPage;
  let fixture: ComponentFixture<SigninInfoPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SigninInfoPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SigninInfoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
