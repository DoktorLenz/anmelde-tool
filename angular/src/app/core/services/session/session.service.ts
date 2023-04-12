import { Injectable } from '@angular/core';
import { LocalStorageService } from '../local-storage/local-storage.service';
import { Authority } from './authority';

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  constructor(private readonly localStorageService: LocalStorageService) {}

  public isSessionAuthenticated(): boolean {
    return this.localStorageService.getSessionAuthenticated();
  }

  public setSessionDetails(authenticated: boolean, authorities: Authority[]) {
    this.localStorageService.setSessionAuthenticated(authenticated);
    this.localStorageService.setSessionAuthorities(authorities);
  }
}
