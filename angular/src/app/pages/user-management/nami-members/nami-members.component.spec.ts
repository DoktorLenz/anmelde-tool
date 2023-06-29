import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NamiMembersComponent } from './nami-members.component';

describe('NamiMembersComponent', () => {
  let component: NamiMembersComponent;
  let fixture: ComponentFixture<NamiMembersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NamiMembersComponent],
    });
    fixture = TestBed.createComponent(NamiMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
