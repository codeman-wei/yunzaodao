import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CreateSigninPage } from './create-signin.page';

describe('CreateSigninPage', () => {
  let component: CreateSigninPage;
  let fixture: ComponentFixture<CreateSigninPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSigninPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateSigninPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
