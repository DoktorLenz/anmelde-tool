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

  public setSessionAuthorities(authorities: Authority[] | null) {
    this.toLocalStorage(authorities, LocalStorageKey.SESSION_AUTHORITIES);
  }

  public getSessionAuthorities(): Authority[] | null {
    return this.fromLocalStorage(LocalStorageKey.SESSION_AUTHORITIES);
  }

  //#region Storage-Logic

  private toLocalStorage<T>(
    item: T,
    localStorageKey: LocalStorageKey,
  ): void {
    try {
      if (this.isPrimitive(item)) {
        localStorage.setItem(localStorageKey.toString(), this.b64EncodeUnicode(item.toString()));
      } else {
        localStorage.setItem(
          localStorageKey.toString(),
          this.b64EncodeUnicode(JSON.stringify(item)),
        );
      }
    } catch (e) {
      console.error(e);
    }
  }

  private fromLocalStorage<T>(localStorageKey: LocalStorageKey): T | null {
    const obj = localStorage.getItem(localStorageKey.toString());
    if (!obj) {
      return null;
    }
    let decoded: string | null = null;
    try {
      decoded = this.b64DecodeUnicode(obj);
      return JSON.parse(decoded) as T;
    } catch (e) {
      console.error(`Value of ${localStorageKey} could not be decoded`);
      return null;
    }
  }

  private isPrimitive(value: unknown): value is boolean | number | string {
    const type = typeof value;
    return type === 'string' || type === 'number' || type === 'boolean';
  }

  private b64EncodeUnicode(a_str: string): string {
    // eslint-disable-next-line deprecation/deprecation
    return btoa(
      encodeURIComponent(a_str).replace(
        /%([0-9A-F]{2})/g,
        function (a_match, a_p1) {
          return String.fromCharCode(parseInt(a_p1, 16));
        },
      ),
    );
  }

  /**
   * Decodes a string encoded by {@link b64EncodeUnicode}
   * @param a_str String to decode
   * @returns Decoded String
   * @throws Decoding-Error
   */
  private b64DecodeUnicode(a_str: string): string {
    return decodeURIComponent(
      Array.prototype.map
        // eslint-disable-next-line deprecation/deprecation
        .call(atob(a_str), function (a_c: string) {
          return '%' + ('00' + a_c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join(''),
    );
  }
  //#endregion
}
