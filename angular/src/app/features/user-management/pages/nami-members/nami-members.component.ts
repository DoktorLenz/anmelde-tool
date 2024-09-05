import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NamiMember } from '../../models/nami-member';
import { userManagementFeature } from '../../ngrx';
import * as UserManagementActions from '../../ngrx/user-management.actions';

@Component({
  templateUrl: './nami-members.component.html',
})
export class NamiMembersComponent implements OnInit {
  protected namiMembers: NamiMember[] = [];

  protected namiFetchDialogVisible = false;
  protected userAssignmentsDialogVisible = false;

  protected gridLoading = true;

  protected username = '';

  protected password = '';

  protected groupId = '';

  protected memberId: number | null = null;

  constructor(private readonly store: Store) {}

  ngOnInit(): void {
    this.refreshList();
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
    this.namiFetchDialogVisible = false;
    this.store.dispatch(
      UserManagementActions.namiImportInitiate({
        username: this.username,
        password: this.password,
        groupingId: this.groupId,
      })
    );
  }

  protected onEditUserAssignments(member: NamiMember): void {
    this.memberId = member.memberId;
    this.userAssignmentsDialogVisible = true;
  }
}
