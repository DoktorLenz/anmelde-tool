import { Component, OnInit } from '@angular/core';
import { HttpSessionService } from './core/http/http-session/http-session.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'at-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [MessageService],
})
export class AppComponent implements OnInit {
  title = 'anmelde-tool-frontend';

  constructor(
    private readonly httpSessionSerivce: HttpSessionService,
    private readonly messageService: MessageService) {

  }

  public ngOnInit(): void {
    this.httpSessionSerivce.session().subscribe({
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Störungen',
          detail: 'Derzeit gibt es in unserem System Störungen. Bitte versuche es später erneut.',
          sticky: true,
          closable: false,
        });
      },
    });
  }
}
