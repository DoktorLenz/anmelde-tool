import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NamiMembersService } from 'src/app/user-management/nami-members/services/nami-members.service';
import { userManagementFeature } from '../reducers';
import * as UserManagementActions from '../user-management.actions';
import { NamiMember } from './model/nami-member';

@Component({
  templateUrl: './nami-members.component.html',
})
export class NamiMembersComponent implements OnInit {
  protected namiMembers: NamiMember[] = [];

  protected namiFetchDialogVisible = false;
  protected legalGuardianDialogVisible = false;

  protected gridLoading = true;

  protected username = '';

  protected password = '';

  protected groupId = '';

  protected memberId: number | null = null;

  constructor(
    private readonly namiMembersService: NamiMembersService,
    private readonly store: Store
  ) {}

  ngOnInit(): void {
    this.store.dispatch(UserManagementActions.loadNamiMembersInitiate());
  }

  protected namiMembers$: Observable<NamiMember[]> = this.store.select(
    userManagementFeature.selectNamiMembers
  );

  protected loadingNamiMembers$: Observable<boolean> = this.store.select(
    userManagementFeature.selectLoadingNamiMembers
  );

  protected refreshList(): void {
    this.store.dispatch(UserManagementActions.loadNamiMembersInitiate());
  }

  protected startNamiImport(): void {
    this.gridLoading = true;
    this.namiFetchDialogVisible = false;
    this.namiMembersService
      .fetchNamiMembers({
        username: this.username,
        password: this.password,
        groupingId: this.groupId,
      })
      .subscribe(() => {
        this.refreshList();
      });
  }

  protected onEditLegalGuardian(member: NamiMember): void {
    this.memberId = member.memberId;
    this.legalGuardianDialogVisible = true;
  }
}
