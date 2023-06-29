import { Injectable } from '@angular/core';
import { LocalStorageKey } from './local-storage-key.enum';
import { UserData } from 'src/app/auth/models/user-data';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {

  private prefix = 'anmelde-tool';

  //#region UserData
  public get userData(): UserData {
    return this.getItem<UserData>(LocalStorageKey.USER_DATA) ?? {};
  }

  public set userData(userData: UserData) {
    this.setItem(LocalStorageKey.USER_DATA, userData);
  }
  //#endregion

  private getItem<T>(key: LocalStorageKey): T | null {
    const item: string | null = localStorage.getItem(`${this.prefix}.${key}`);
    if (!item) {
      return null;
    }
    return JSON.parse(item) as T;
  }

  private setItem<T>(key: LocalStorageKey, item: T): void {
    localStorage.setItem(`${this.prefix}.${key}`, JSON.stringify(item));
  }
}
