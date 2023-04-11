import { ChangeDetectorRef, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { useAnimation } from '@angular/animations';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { MenuService } from './app.menu.service';
import { AppMainComponent } from '../main/app.main.component';
import { menuAnimation } from './app.menu.animation';

@Component({
  selector: '[at-menuitem]',
  templateUrl: './app.menuitem.component.html',
  host: {
    '[class.active-menuitem]': 'active',
  },
  animations: [useAnimation(menuAnimation)],
})
export class AppMenuitemComponent implements OnInit, OnDestroy {

  @Input() item: any = {};

  @Input() index = 0;

  @Input() root = false;

  @Input() parentKey = '';

  active = false;

  menuSourceSubscription: Subscription;

  menuResetSubscription: Subscription;

  key = '';

  constructor(public app: AppMainComponent, public router: Router, private cd: ChangeDetectorRef, private menuService: MenuService) {
    this.menuSourceSubscription = this.menuService.menuSource$.subscribe(key => {
      // deactivate current active menu
      if (this.active && this.key !== key && key.indexOf(this.key) !== 0) {
        this.active = false;
      }
    });

    this.menuResetSubscription = this.menuService.resetSource$.subscribe(() => {
      this.active = false;
    });

    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        if ((this.app.isHorizontal() || this.app.isSlim())) {
          this.active = false;
        } else {
          if (this.item.routerLink) {
            this.updateActiveStateFromRoute();
          } else {
            this.active = false;
          }
        }
      });
  }

  ngOnInit() {
    if (!(this.app.isHorizontal() || this.app.isSlim()) && this.item.routerLink) {
      this.updateActiveStateFromRoute();
    }

    this.key = this.parentKey ? this.parentKey + '-' + this.index : String(this.index);
  }

  updateActiveStateFromRoute() {
    this.active = this.router.isActive(this.item.routerLink[0], !this.item.items && !this.item.preventExact);
  }

  itemClick(event: Event) {
    // avoid processing disabled items
    if (this.item.disabled) {
      event.preventDefault();
      return;
    }

    // navigate with hover in horizontal mode
    if (this.root) {
      this.app.menuHoverActive = !this.app.menuHoverActive;
    }

    // notify other items
    this.menuService.onMenuStateChange(this.key);

    // execute command
    if (this.item.command) {
      this.item.command({ originalEvent: event, item: this.item });
    }

    // toggle active state
    if (this.item.items) {
      this.active = !this.active;
    } else {
      // activate item
      this.active = true;

      // reset horizontal || slim menu
      if (this.app.isHorizontal() || this.app.isSlim()) {
        this.menuService.reset();
      }

      // hide overlay menus
      this.app.overlayMenuActive = false;
      this.app.staticMenuMobileActive = false;
      this.app.menuHoverActive = !this.app.menuHoverActive;
    }
  }

  onMouseEnter() {
    // activate item on hover
    if (this.root && this.app.menuHoverActive && (this.app.isHorizontal() || this.app.isSlim()) && this.app.isDesktop()) {
      this.menuService.onMenuStateChange(this.key);
      this.active = true;
    }
  }

  ngOnDestroy() {
    if (this.menuSourceSubscription) {
      this.menuSourceSubscription.unsubscribe();
    }

    if (this.menuResetSubscription) {
      this.menuResetSubscription.unsubscribe();
    }
  }
}
