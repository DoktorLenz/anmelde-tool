import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './registration.component';
import { MockProviders } from 'ng-mocks';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      providers: [
        MockProviders(HttpAuthService, Router, MessageService),
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
