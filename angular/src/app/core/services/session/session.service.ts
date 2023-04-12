import { Injectable } from '@angular/core';
import { LocalStorageService } from '../local-storage/local-storage.service';
import { Authority } from './authority';

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  constructor(private readonly localStorageService: LocalStorageService) {}

  public get sessionAuthenticated(): boolean {
    return this.localStorageService.sessionAuthenticated;
  }

  public setSessionDetails(authenticated: boolean, authorities: Authority[]) {
    this.localStorageService.sessionAuthenticated = authenticated;
    this.localStorageService.sessionAuthorities = authorities;
  }
}
