import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NamiMember } from '../../models/nami-member';
import { userManagementFeature } from '../../ngrx';
import * as UserManagementActions from '../../ngrx/user-management.actions';
import { NamiMembersService } from '../../services/nami-members.service';

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
