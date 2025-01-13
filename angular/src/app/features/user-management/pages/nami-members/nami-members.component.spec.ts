import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NamiMembersComponent } from './nami-members.component';
import { MockProvider } from 'ng-mocks';
import { NamiMembersService } from 'src/app/user-management/nami-members/services/nami-members.service';
import { NEVER } from 'rxjs';

describe('NamiMembersComponent', () => {
  let component: NamiMembersComponent;
  let fixture: ComponentFixture<NamiMembersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    imports: [NamiMembersComponent],
    providers: [
        MockProvider(NamiMembersService, {
            getNamiMembers: jasmine.createSpy().and.returnValue(NEVER),
        }),
    ],
});
    fixture = TestBed.createComponent(NamiMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
