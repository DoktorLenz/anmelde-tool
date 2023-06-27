import { MockBuilder, MockRender } from 'ng-mocks';
import { BreakpointDirective } from './breakpoint.directive';
import { Breakpoint } from './breakpoint.enum';
import { NumberComparator } from './comparator';

describe('Breakpoint directive with Comparator SmallerThan', () => {
  beforeEach(() => MockBuilder(BreakpointDirective));

  it('should show element when screen is not large', () => {
    const view = MockRender('<div *breakpoint="breakpoint; comparator comp">content</div>', {
      breakpoint: Breakpoint.LARGE,
      comp: NumberComparator.less,
    });

    spyOnProperty(window, 'innerWidth').and.returnValue(Breakpoint.LARGE - 1);

    window.dispatchEvent(new Event('resize'));

    expect(view.nativeElement.innerHTML).toContain('content');
  });

  it('should hide element when screen is large', () => {
    const view = MockRender('<div *breakpoint="breakpoint; comparator comp">content</div>', {
      breakpoint: Breakpoint.LARGE,
      comp: NumberComparator.less,
    });

    spyOnProperty(window, 'innerWidth').and.returnValue(Breakpoint.LARGE);

    window.dispatchEvent(new Event('resize'));

    expect(view.nativeElement.innerHTML).not.toContain('content');
  });
});


describe('Breakpoint directive with not comparator', () => {
  beforeEach(() => MockBuilder(BreakpointDirective));

  it('should hide element regardless of screen size', () => {
    const view = MockRender('<div *breakpoint="breakpoint">content</div>', {
      breakpoint: Breakpoint.LARGE,
    });

    spyOnProperty(window, 'innerWidth').and.returnValue(Breakpoint.LARGE - 1);
    window.dispatchEvent(new Event('resize'));

    expect(view.nativeElement.innerHTML).not.toContain('content');

    spyOnProperty(window, 'innerWidth').and.returnValue(Breakpoint.LARGE);
    window.dispatchEvent(new Event('resize'));

    expect(view.nativeElement.innerHTML).not.toContain('content');
  });
});
