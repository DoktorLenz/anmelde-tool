import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { NavigationService } from '../navigation.service';
import { Observable, filter, tap } from 'rxjs';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';
import { NumberComparator } from 'src/app/layout/directives/breakpoint/comparator';
import { UserDataService } from 'src/app/auth/services/userdata/user-data.service';
import { UserData } from 'src/app/auth/models/user-data';
import { Role } from 'src/app/auth/models/role.enum';
import { BaseRoute } from 'src/app/lib/routes/base-route.enum';
import { UserManagementRoute } from 'src/app/lib/routes/user-management-route.enum';
import { EventType, Router } from '@angular/router';

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
      routerLink: `${BaseRoute.HOME}`,
    },
  ];

  private adminMenuItems: MenuItem[] = [
    {
      label: 'Nutzerverwaltung',
      icon: 'pi pi-users',
      items: [
        {
          label: 'Übersicht',
          routerLink: `${BaseRoute.USER_MANAGEMENT}/${UserManagementRoute.OVERVIEW}`,
        },
        {
          label: 'NaMi-Mitglieder',
          routerLink: `${BaseRoute.USER_MANAGEMENT}/${UserManagementRoute.NAMI_MEMBERS}`,
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
    private readonly router: Router,
  ) {
    this.userDataService.userData$.subscribe({
      next: (userData: UserData) => {
        this.menuItems = [...this.userMenuItems];
        if (userData.authorities?.find((role) => role === Role.ADMIN)) {
          this.menuItems.push(...this.adminMenuItems);
        }
      },
    });

    this.router.events
      .pipe(
        filter(
          (routerEvent) =>
            routerEvent.type === EventType.NavigationStart ||
            routerEvent.type === EventType.NavigationSkipped,
        ),
      ).subscribe(() => {
        this.sidebarVisibleChange(false);
      });
  }
}
