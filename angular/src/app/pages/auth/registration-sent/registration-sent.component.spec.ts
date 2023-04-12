import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrationSentComponent } from './registration-sent.component';

describe('RegistrationSentComponent', () => {
  let component: RegistrationSentComponent;
  let fixture: ComponentFixture<RegistrationSentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrationSentComponent ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegistrationSentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
