import { Directive, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { UserDataService } from '../../services/userdata/user-data.service';
import { UserData } from '../../services/userdata/models/user-data';

@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[hasRole]',
})
export class HasRoleDirective implements OnInit {

  private _requiredRoles: string[] = [];

  private _isHidden = true;

  constructor(
    private readonly templateRef: TemplateRef<unknown>,
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
    this._requiredRoles = roles;
    this.updateView();
  }

  private updateView(): void {
    this.userDataService.userData$.subscribe({
      next: (userData: UserData) => {
        if (this.checkRole(userData)) {
          if (this._isHidden) {
            this.viewContainer.createEmbeddedView(this.templateRef);
            this._isHidden = false;
          }
        } else {
          this._isHidden = true;
          this.viewContainer.clear();
        }
      },
      error: () => console.error('ERROR'),
      complete: () => console.warn('COMPLETE'),
    });

  }

  private checkRole(userData: UserData): boolean {
    let hasRole = false;

    if (this._requiredRoles.length === 0) {
      return true;
    }

    if (userData.authorities) {
      for (const neededRole of this._requiredRoles) {
        hasRole = userData.authorities.find(role => role.toString() === neededRole) ? true : false;
        if (hasRole) {
          break;
        }
      }
    }

    return hasRole;
  }

}
