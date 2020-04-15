import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { JoinClassPage } from './join-class.page';

describe('JoinClassPage', () => {
  let component: JoinClassPage;
  let fixture: ComponentFixture<JoinClassPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JoinClassPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(JoinClassPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
