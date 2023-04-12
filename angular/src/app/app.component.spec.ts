import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { MockProviders, ngMocks } from 'ng-mocks';
import { HttpSessionService } from './core/http/http-session/http-session.service';
import { MessageService } from 'primeng/api';
import { of, throwError } from 'rxjs';

describe('AppComponent', () => {
  let httpSessionSerivce: HttpSessionService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
      ],
      declarations: [
        AppComponent,
      ],
      providers: [
        MockProviders(HttpSessionService, MessageService),
      ],
    }).compileComponents();

    httpSessionSerivce = TestBed.inject(HttpSessionService);
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    expect(app).toBeTruthy();
  });

  it('should have as title \'angular\'', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    expect(app.title).toEqual('anmelde-tool-frontend');
  });

  it('should try to retrieve session information at startup', () => {
    ngMocks.stub(httpSessionSerivce, {
      session: jasmine.createSpy().and.returnValue(of()),
    });

    const fixture = TestBed.createComponent(AppComponent);
    const component = fixture.componentInstance;
    component.ngOnInit();

    expect(httpSessionSerivce.session).toHaveBeenCalledWith();
  });

  it('should not add a message if session information can be retrieved', () => {
    const messageAddSpy = spyOn(MessageService.prototype, 'add').and.callFake(() => {});

    ngMocks.stub(httpSessionSerivce, {
      session: jasmine.createSpy().and.returnValue(of()),
    });

    const fixture = TestBed.createComponent(AppComponent);
    const component = fixture.componentInstance;
    component.ngOnInit();

    expect(messageAddSpy).not.toHaveBeenCalled();
  });

  it('should add a message if no session information can be retrieved', () => {
    const messageAddSpy = spyOn(MessageService.prototype, 'add').and.callFake(() => {});

    ngMocks.stub(httpSessionSerivce, {
      session: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
    });

    const fixture = TestBed.createComponent(AppComponent);
    const component = fixture.componentInstance;
    component.ngOnInit();

    expect(messageAddSpy).toHaveBeenCalledWith(jasmine.objectContaining({ 'severity': 'error' }));
  });
});
