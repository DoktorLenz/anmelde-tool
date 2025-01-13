import {
  ChangeDetectionStrategy,
  Component,
  ViewEncapsulation,
} from '@angular/core';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';
import { NumberComparator } from 'src/app/layout/directives/breakpoint/comparator';
import { NavigationService } from '../navigation.service';
import { Menubar } from 'primeng/menubar';
import { PrimeTemplate } from 'primeng/api';
import { BreakpointDirective } from '../../layout/directives/breakpoint/breakpoint.directive';
import { Button } from 'primeng/button';
import { Avatar } from 'primeng/avatar';

@Component({
    selector: 'sv-header',
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss',
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [Menubar, PrimeTemplate, BreakpointDirective, Button, Avatar]
})
export class HeaderComponent {
  protected Breakpoint = Breakpoint;

  protected NumberComparator = NumberComparator;

  constructor(private readonly navigationService: NavigationService) {}

  protected showSidebar(): void {
    this.navigationService.sidebarVisible(true);
  }
}
