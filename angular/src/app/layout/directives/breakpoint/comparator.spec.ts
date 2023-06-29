import { NumberComparator } from './comparator';

describe('NumberComparator.greater', () => {
  it('should return true if value a is greater than b', () => {
    expect(NumberComparator.greater(2, 1)).toBeTrue();
  });

  it('should return false if value a is less than or equal to b', () => {
    expect(NumberComparator.greater(1, 1)).toBeFalse();
    expect(NumberComparator.greater(1, 2)).toBeFalse();
  });
});

describe('NumberComparator.greaterEqual', () => {
  it('should return true if value a is greater than or equal to b', () => {
    expect(NumberComparator.greaterEquals(2, 1)).toBeTrue();
    expect(NumberComparator.greaterEquals(2, 2)).toBeTrue();
  });

  it('should return false if value a is less than b', () => {
    expect(NumberComparator.greaterEquals(1, 2)).toBeFalse();
  });
});

describe('NumberComparator.less', () => {
  it('should return true if value a is less than', () => {
    expect(NumberComparator.less(1, 2)).toBeTrue();
  });

  it('should return false if value a is greater than or equal to b', () => {
    expect(NumberComparator.less(1, 1)).toBeFalse();
    expect(NumberComparator.less(2, 1)).toBeFalse();
  });
});

describe('NumberComparator.lessEquals', () => {
  it('should return true if value a is less than or equal to b', () => {
    expect(NumberComparator.lessEquals(1, 2)).toBeTrue();
    expect(NumberComparator.lessEquals(1, 1)).toBeTrue();
  });

  it('should return false if value a is greater than b', () => {
    expect(NumberComparator.lessEquals(2, 1)).toBeFalse();
  });
});

describe('NumberComparator.equals', () => {
  it('should return true if value a is equal to b', () => {
    expect(NumberComparator.equals(1, 1)).toBeTrue();
  });

  it('should return false if value a is greater or less than b', () => {
    expect(NumberComparator.equals(2, 1)).toBeFalse();
    expect(NumberComparator.equals(1, 2)).toBeFalse();
  });
});
