import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable()
export class NavigationService {
  private _sidebarVisible: BehaviorSubject<boolean> = new BehaviorSubject(false);

  public sidebarVisible$ = this._sidebarVisible.asObservable();

  public sidebarVisible(visible: boolean): void {
    this._sidebarVisible.next(visible);
  }
}
