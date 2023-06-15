import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { NavigationService } from '../navigation.service';
import { Observable } from 'rxjs';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';
import { NumberComparator } from 'src/app/layout/directives/breakpoint/comparator';
import { UserDataService } from 'src/app/auth/services/userdata/user-data.service';
import { UserData } from 'src/app/auth/services/userdata/models/user-data';
import { Role } from 'src/app/auth/services/userdata/models/role.enum';

@Component({
  selector: 'at-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SidebarComponent {

  private userMenuItems: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
    },
  ];

  private adminMenuItems: MenuItem[] = [
    {
      label: 'Nutzerverwaltung',
      icon: 'pi pi-users',
      items: [
        {
          label: 'Übersicht',
        },
        {
          label: 'NaMi-Mitglieder',
        },
      ],
    },
  ];

  protected menuItems: MenuItem[] = [];

  protected get sidebarVisible$(): Observable<boolean> {
    return this.navigationService.sidebarVisible$;
  }

  protected sidebarVisibleChange(visible: boolean): void {
    this.navigationService.sidebarVisible(visible);
  }

  protected Breakpoint = Breakpoint;

  protected NumberComparator = NumberComparator;

  constructor(
    private readonly navigationService: NavigationService,
    private readonly userDataService: UserDataService,
    private readonly cdr: ChangeDetectorRef,
  ) {
    this.userDataService.userData$.subscribe({
      next: (userData: UserData) => {
        this.menuItems = [...this.userMenuItems];
        if (userData.authorities?.find((role) => role === Role.ADMIN)) {
          this.menuItems.push(...this.adminMenuItems);
        }
        this.cdr.detectChanges();
      },
      error: () => {
        this.menuItems = [];
      },
      complete: () => {

      },
    });
  }
}
