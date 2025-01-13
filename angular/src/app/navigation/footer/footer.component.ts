import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
    selector: 'sv-footer',
    templateUrl: './footer.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
    standalone: false
})
export class FooterComponent {}
