import {
  Directive,
  HostListener,
  Input,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { Breakpoint } from './breakpoint.enum';
import { ComparatorFn } from './comparator';

@Directive({
  selector: '[breakpoint]',
})
export class BreakpointDirective implements OnInit {
  @Input()
  public set breakpoint(breakpoint: Breakpoint) {
    this._breakpoint = breakpoint;
  }

  @Input()
  public set breakpointComparator(comparatorFn: ComparatorFn<number>) {
    this._comparatorFn = comparatorFn;
  }

  private _breakpoint: Breakpoint = Breakpoint.NONE;

  private _comparatorFn: ComparatorFn<number> = () => false;

  private _isHidden = true;

  constructor(
    private readonly _templateRef: TemplateRef<unknown>,
    private readonly _viewContainer: ViewContainerRef
  ) {}

  @HostListener('window:resize')
  public onResize(): void {
    this.updateView();
  }

  public ngOnInit(): void {
    this.updateView();
  }

  private updateView(): void {
    const screenWidth = window.innerWidth;
    if (this._comparatorFn(screenWidth, this._breakpoint)) {
      if (this._isHidden) {
        this._viewContainer.createEmbeddedView(this._templateRef);
        this._isHidden = false;
      }
    } else {
      this._isHidden = true;
      this._viewContainer.clear();
    }
  }
}
