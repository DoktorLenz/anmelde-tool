import { Injectable } from '@angular/core';
import { LocalStorageKey } from './local-storage-key';
import { Authority } from '../session/authority';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {

  public setSessionAuthenticated(authenticated: boolean) {
    this.toLocalStorage(authenticated, LocalStorageKey.SESSION_AUTHENTICATED);
  }

  public getSessionAuthenticated(): boolean {
    return this.fromLocalStorage(LocalStorageKey.SESSION_AUTHENTICATED) ?? false;
  }

  public setSessionAuthorities(authorities: Authority[]) {
    this.toLocalStorage(authorities, LocalStorageKey.SESSION_AUTHORITIES);
  }

  public getSessionAuthorities(): Authority[] {
    return this.fromLocalStorage(LocalStorageKey.SESSION_AUTHORITIES) ?? [];
  }

  //#region Storage-Logic

  private toLocalStorage<T>(
    item: T,
    localStorageKey: LocalStorageKey,
  ): void {
    if (this.isPrimitive(item)) {
      localStorage.setItem(localStorageKey.toString(), item.toString());
    } else {
      localStorage.setItem(
        localStorageKey.toString(),
        JSON.stringify(item),
      );
    }
  }

  private fromLocalStorage<T>(localStorageKey: LocalStorageKey): T | null {
    const obj = localStorage.getItem(localStorageKey.toString());
    if (!obj) {
      return null;
    }
    try {
      return JSON.parse(obj) as T;
    } catch (e) {
      console.error(e);
      return null;
    }
  }

  private isPrimitive(value: unknown): value is boolean | number | string {
    const type = typeof value;
    return type === 'string' || type === 'number' || type === 'boolean';
  }
  //#endregion
}
