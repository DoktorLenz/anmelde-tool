import { TestBed, waitForAsync } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AppMenuComponent } from './menu/app.menu.component';
import { AppFooterComponent } from './footer/app.footer.component';
import { AppRightPanelComponent } from './rightpanel/app.rightpanel.component';
import { TabViewModule } from 'primeng/tabview';
import { AppTopBarComponent } from './topbar/app.topbar.component';
import { AppBreadcrumbComponent } from './breadcrumb/app.breadcrumb.component';
import { BreadcrumbService } from './breadcrumb/app.breadcrumb.service';
import { MenuService } from './menu/app.menu.service';

describe('AppComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        TabViewModule,
      ],
      declarations: [
        AppComponent,
        AppMenuComponent,
        AppRightPanelComponent,
        AppTopBarComponent,
        AppFooterComponent,
        AppBreadcrumbComponent,
      ],
      providers: [BreadcrumbService, MenuService],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;

    expect(app).toBeTruthy();
  });
});
