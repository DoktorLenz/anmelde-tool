import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';

import { TabViewModule } from 'primeng/tabview';

import { AppComponent } from './app.component';
import { AppRightPanelComponent } from './rightpanel/app.rightpanel.component';
import { AppMenuComponent } from './menu/app.menu.component';
import { AppFooterComponent } from './footer/app.footer.component';
import { AppBreadcrumbComponent } from './breadcrumb/app.breadcrumb.component';
import { BreadcrumbService } from './breadcrumb/app.breadcrumb.service';
import { AppMainComponent } from './main/app.main.component';
import { MenuService } from './menu/app.menu.service';
import { AppMenuitemComponent } from './menu/app.menuitem.component';
import { AppTopBarComponent } from './topbar/app.topbar.component';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    TabViewModule,
  ],
  declarations: [
    AppComponent,
    AppMainComponent,
    AppMenuComponent,
    AppMenuitemComponent,
    AppTopBarComponent,
    AppFooterComponent,
    AppBreadcrumbComponent,
    AppRightPanelComponent,
  ],
  providers: [
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    BreadcrumbService, MenuService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
