import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'sv-footer',
  templateUrl: './footer.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FooterComponent {}
