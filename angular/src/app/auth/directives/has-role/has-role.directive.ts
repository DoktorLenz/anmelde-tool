import { Directive, ElementRef, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { UserDataService } from '../../services/userdata/user-data.service';
import { Role } from '../../services/userdata/models/role.enum';
import { UserData } from '../../services/userdata/models/user-data';

@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[hasRole]',
})
export class HasRoleDirective implements OnInit {

  private requiredRoles: string[] = [];

  constructor(
    private readonly element: ElementRef,
    private readonly templateRef: TemplateRef<any>,
    private readonly viewContainer: ViewContainerRef,
    private readonly userDataService: UserDataService,
  ) { }


  ngOnInit(): void {
    this.userDataService.userData$.subscribe(() => {
      this.updateView();
    });
  }

  /**
   * One of the role is sufficient
   */
  @Input()
  set hasRole(roles: string[]) {
    this.requiredRoles = roles;
    this.updateView();
  }

  private updateView(): void {
    this.userDataService.userData$.subscribe({
      next: (userData: UserData) => {
        if (this.checkRole(userData)) {
          this.viewContainer.createEmbeddedView(this.templateRef);
        } else {
          this.viewContainer.clear();
        }
      },
      error: () => console.error('ERROR'),
      complete: () => console.warn('COMPLETE'),
    });

  }

  private checkRole(userData: UserData): boolean {
    let hasRole = false;

    if (this.requiredRoles.length === 0) {
      return true;
    }

    if (userData.authorities) {
      for (const neededRole of this.requiredRoles) {
        hasRole = userData.authorities.find(role => role.toString() === neededRole) ? true : false;
        if (hasRole) {
          break;
        }
      }
    }

    return hasRole;
  }

}
