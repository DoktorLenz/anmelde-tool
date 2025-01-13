import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { JwtPayload, jwtDecode } from 'jwt-decode';
import { switchMap } from 'rxjs';
import { setRoles } from 'src/app/auth/auth.actions';
import { Role } from 'src/app/auth/models/role.enum';
import { BaseRoute } from 'src/app/lib/routes/base-route.enum';

@Component({
    template: '<p>You are logged in. Redirecting...</p>',
    standalone: false
})
export class CallbackComponent implements OnInit {
  constructor(
    private readonly securityService: OidcSecurityService,
    private readonly router: Router,
    private readonly store: Store
  ) {}

  public ngOnInit(): void {
    this.securityService
      .checkAuth()
      .pipe(
        switchMap(() => {
          return this.router.navigate([BaseRoute.HOME]);
        })
      )
      .subscribe({
        next: () => {
          this.securityService.getAccessToken().subscribe({
            next: token => {
              const roles = this.getRolesFromJwt(token).map(
                role => role as Role
              );
              this.store.dispatch(setRoles({ roles }));
            },
          });
        },
      });
  }

  private getRolesFromJwt(token: string): string[] {
    interface JwtPayloadExtension extends JwtPayload {
      realm_access?: {
        roles?: string[];
      };
    }
    return jwtDecode<JwtPayloadExtension>(token).realm_access?.roles ?? [];
  }
}
