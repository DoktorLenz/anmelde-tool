import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { NavigationService } from '../navigation.service';
import { Observable } from 'rxjs';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';
import { NumberComparator } from 'src/app/layout/directives/breakpoint/comparator';

@Component({
  selector: 'at-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SidebarComponent {

  protected menuItems: MenuItem[] = [
    {
      label: 'Home',
      icon: 'pi pi-home',
    },
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
  ) {
  }
}
