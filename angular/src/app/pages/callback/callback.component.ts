import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { switchMap } from 'rxjs';
import { BaseRoute } from 'src/app/lib/routes/base-route';

@Component({
  template: '<p>You are logged in. Redirecting...</p>',
})
export class CallbackComponent implements OnInit {
  constructor(
    private readonly securityService: OidcSecurityService,
    private readonly router: Router,
  ) {}

  public ngOnInit(): void {
    this.securityService.checkAuth()
      .pipe(
        switchMap(() =>{
          return this.router.navigate([BaseRoute.HOME]);
        }),
      )
      .subscribe(() => {
      });
  }
}
