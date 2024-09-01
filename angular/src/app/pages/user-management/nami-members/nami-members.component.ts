import { Component, OnInit } from '@angular/core';
import { NamiMember } from 'src/app/user-management/nami-members/model/nami-member';
import { NamiMembersService } from 'src/app/user-management/nami-members/services/nami-members.service';

@Component({
  templateUrl: './nami-members.component.html',
})
export class NamiMembersComponent implements OnInit {
  protected namiMembers: NamiMember[] = [];

  protected namiFetchDialogVisible = false;

  protected gridLoading = true;

  protected username = '';

  protected password = '';

  protected groupId = '';

  constructor(private readonly namiMembersService: NamiMembersService) {}

  ngOnInit(): void {
    this.refreshList();
  }

  protected refreshList(): void {
    this.gridLoading = true;
    this.namiMembersService.getNamiMembers().subscribe(namiMembers => {
      this.namiMembers = namiMembers;
      this.gridLoading = false;
    });
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
}
