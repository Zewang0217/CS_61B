public class OffByOne implements CharacterComparator {

  /**
   * This interface defines a method for determining equality of characters
   */

  @Override
  public boolean equalChars(char x, char y) {
    return Math.abs(x - y) == 1;
  }
}
