import { Directive, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { UserDataService } from '../../services/userdata/user-data.service';
import { Role } from '../../models/role.enum';

@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[hasRole]',
})
export class HasRoleDirective implements OnInit {

  private _requiredRole!: Role;

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
  set hasRole(role: Role) {
    this._requiredRole = role;
    this.updateView();
  }

  private updateView(): void {
    this.userDataService.hasRole(this._requiredRole).subscribe({
      next: (hasRole: boolean) => {
        if (hasRole) {
          if (this._isHidden) {
            this.viewContainer.createEmbeddedView(this.templateRef);
            this._isHidden = false;
          }
        } else {
          this._isHidden = true;
          this.viewContainer.clear();
        }
      },
    });
  }
}
