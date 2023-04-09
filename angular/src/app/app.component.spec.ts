import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AppMenuComponent } from './menu/app.menu.component';
import { AppTopBarComponent } from './app.topbar.component';
import { AppFooterComponent } from './footer/app.footer.component';
import { AppRightPanelComponent } from './rightpanel/app.rightpanel.component';
import { AppBreadcrumbComponent } from './app.breadcrumb.component';
import { BreadcrumbService } from './app.breadcrumb.service';
import { TabViewModule } from 'primeng/tabview';
import { MenuService } from './app.menu.service';

describe('AppComponent', () => {
  beforeEach(async(() => {
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

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;

    expect(app).toBeTruthy();
  }));
});
