import { AsyncPipe, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Store } from '@ngrx/store';
import { PrimeTemplate } from 'primeng/api';
import { Button } from 'primeng/button';
import { Chip } from 'primeng/chip';
import { Dialog } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { MultiSelect, MultiSelectChangeEvent } from 'primeng/multiselect';
import { Password } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { Tag } from 'primeng/tag';
import { Observable } from 'rxjs';
import { RankColorClassPipe } from '../../../../lib/pipes/rank-color-class/rank-color-class.pipe';
import { NamiMember } from '../../models/nami-member';
import { User } from '../../models/user';
import { userManagementFeature } from '../../ngrx';
import * as UserManagementActions from '../../ngrx/user-management.actions';

@Component({
  templateUrl: './nami-members.component.html',
  imports: [
    TableModule,
    PrimeTemplate,
    Button,
    NgIf,
    Tag,
    MultiSelect,
    FormsModule,
    NgFor,
    Chip,
    Dialog,
    InputText,
    Password,
    AsyncPipe,
    RankColorClassPipe,
  ],
})
export class NamiMembersComponent implements OnInit {
  protected namiFetchDialogVisible = false;

  protected gridLoading = true;

  protected username = '';

  protected password = '';

  protected groupId = '';

  constructor(private readonly store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(UserManagementActions.loadUsersInititate());
    this.refreshList();
  }

  protected namiMembers$: Observable<NamiMember[]> = this.store.select(
    userManagementFeature.selectNamiMembers
  );

  protected loadingNamiMembers$: Observable<boolean> = this.store.select(
    userManagementFeature.selectLoadingNamiMembers
  );

  protected users$: Observable<User[]> = this.store.select(
    userManagementFeature.selectUsers
  );

  protected loadingUsers$: Observable<boolean> = this.store.select(
    userManagementFeature.selectLoadingUsers
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

  protected removeUserAssignment(namiMember: NamiMember, user: User): void {
    this.store.dispatch(
      UserManagementActions.updateNamiMemberInitiate({
        namiMember: {
          ...namiMember,
          userAssignments: namiMember.userAssignments.filter(
            userAssignment => userAssignment.subject !== user.subject
          ),
        },
      })
    );
  }

  protected updateUserAssignment(
    $event: MultiSelectChangeEvent,
    namiMember: NamiMember
  ) {
    this.store.dispatch(
      UserManagementActions.updateNamiMemberInitiate({
        namiMember: {
          ...namiMember,
          userAssignments: $event.value.map((v: User) => ({
            subject: v.subject,
            firstname: v.firstname,
            lastname: v.lastname,
          })),
        },
      })
    );
  }
}
