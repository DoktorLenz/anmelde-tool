import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelMenuModule } from 'primeng/panelmenu';
import { MenubarModule } from 'primeng/menubar';
import { AvatarModule } from 'primeng/avatar';
import { ButtonModule } from 'primeng/button';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { SidebarModule } from 'primeng/sidebar';
import { NavigationService } from './navigation.service';
import { PanelModule } from 'primeng/panel';
import { LayoutDirectivesModule } from '../layout/directives/layout-directives.module';
import { ImageModule } from 'primeng/image';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
  ],
  imports: [
    CommonModule,
    PanelMenuModule,
    MenubarModule,
    AvatarModule,
    ButtonModule,
    SidebarModule,
    PanelModule,
    LayoutDirectivesModule,
    ImageModule,
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
  ],
  providers: [
    NavigationService,
  ],
})
export class NavigationModule { }
