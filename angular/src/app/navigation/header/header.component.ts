import { ChangeDetectionStrategy, Component } from '@angular/core';
import { NavigationService } from '../navigation.service';
import { NumberComparator } from 'src/app/layout/directives/breakpoint/comparator';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';

@Component({
  selector: 'at-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HeaderComponent {

  protected Breakpoint = Breakpoint;

  protected NumberComparator = NumberComparator;

  constructor(private readonly navigationService: NavigationService) {}

  protected showSidebar(): void {
    this.navigationService.sidebarVisible(true);
  }
}
