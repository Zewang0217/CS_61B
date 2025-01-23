public class Sort {
  /**
   * Sorts strings destructively starting from item start.
   * @param x source
   * @param start start index
   */
  private static void sort(String[] x, int start) {
    if (start == x.length) {
      return;
    }
    int smallestIndex = findSmallest(x, start);
    swap(x, start, smallestIndex);
    sort(x, start + 1);
  }
  /**
   * Sorts strings destructively.
   *
   */
  public static void sort(String[] x) {
    //Find the smallest item
    int smallestIndex = findSmallest(x, 0);
    //move it to the front
    swap(x, 0, smallestIndex);
    //selection sort the rest (using recursion?)
    sort(x, 0);
  }

  /**
   * Returns the smallest string in x.
   * @param x string source
   * @return smallest element
   */
  public static int findSmallest(String[] x, int start) {
    int smallestIndex = start;
    for(int i = start; i < x.length; i++) {
      int cmp = x[i].compareTo(x[smallestIndex]);
      if (cmp < 0) {
        smallestIndex = i;
      }
    }
    return smallestIndex;
  }

  /**
   * swap two item.
   * @param x source
   * @param a index1
   * @param b index2
   */
  public static void swap(String[] x, int a, int b) {
    String tmp = x[a];
    x[a] = x[b];
    x[b] = tmp;
  }


}
