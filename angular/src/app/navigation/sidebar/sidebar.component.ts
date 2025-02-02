import { AsyncPipe, NgIf, NgTemplateOutlet } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { EventType, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { MenuItem } from 'primeng/api';
import { PanelMenu } from 'primeng/panelmenu';
import { Sidebar } from 'primeng/sidebar';
import { filter, Observable } from 'rxjs';
import { authFeature } from 'src/app/auth/reducers';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';
import { NumberComparator } from 'src/app/layout/directives/breakpoint/comparator';
import { BaseRoute } from 'src/app/lib/routes/base-route.enum';
import { UserManagementRoute } from 'src/app/lib/routes/user-management-route.enum';
import { BreakpointDirective } from '../../layout/directives/breakpoint/breakpoint.directive';
import { NavigationService } from '../navigation.service';

@Component({
  selector: 'sv-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    BreakpointDirective,
    NgTemplateOutlet,
    Sidebar,
    NgIf,
    PanelMenu,
    AsyncPipe,
  ],
})
export class SidebarComponent {
  protected userMenuItems: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: `${BaseRoute.HOME}`,
    },
  ];

  protected adminMenuItems: MenuItem[] = [
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

  protected get sidebarVisible$(): Observable<boolean> {
    return this.navigationService.sidebarVisible$;
  }

  protected sidebarVisibleChange(visible: boolean): void {
    this.navigationService.sidebarVisible(visible);
  }

  protected Breakpoint = Breakpoint;

  protected NumberComparator = NumberComparator;

  protected isAdmin$ = this.store.select(authFeature.isAdmin);
  protected isVerified$ = this.store.select(authFeature.isVerified);

  constructor(
    private readonly navigationService: NavigationService,
    private readonly router: Router,
    private readonly store: Store
  ) {
    this.router.events
      .pipe(
        filter(
          routerEvent =>
            routerEvent.type === EventType.NavigationStart ||
            routerEvent.type === EventType.NavigationSkipped
        )
      )
      .subscribe(() => {
        this.sidebarVisibleChange(false);
      });
  }
}
