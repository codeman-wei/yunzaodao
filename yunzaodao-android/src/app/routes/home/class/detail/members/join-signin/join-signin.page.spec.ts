import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { JoinSigninPage } from './join-signin.page';

describe('JoinSigninPage', () => {
  let component: JoinSigninPage;
  let fixture: ComponentFixture<JoinSigninPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoinSigninPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(JoinSigninPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
