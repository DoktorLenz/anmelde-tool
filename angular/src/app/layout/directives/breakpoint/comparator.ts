export type ComparatorFn<T> = (...args: T[]) => boolean;

class SimpleComparator<T extends number | string> {
  readonly greater: ComparatorFn<T> = (a: T, b: T) => a > b;

  readonly greaterEquals: ComparatorFn<T> = (a: T, b: T) => a >= b;

  readonly less: ComparatorFn<T> = (a: T, b: T) => a < b;

  readonly lessEquals: ComparatorFn<T> = (a: T, b: T) => a <= b;

  readonly equals: ComparatorFn<T> = (a: T, b: T) => a === b;
}

export const NumberComparator = new SimpleComparator<number>();
export const StringComparator = new SimpleComparator<string>();
