import org.junit.Test;
import static org.junit.Assert.*;

public class TestSort {
  /**
   * Tests the sort method of the sort class.
   */
  @Test
  public void testSort() {
    String[] input = {"i", "have", "an", "egg"};
    String[] expected = {"an", "egg", "have", "i"};
    Sort.sort(input);
    /*ad hoc testing*/
    //for(int i = 0; i < expected.length; i++){
    //if(!input[i].equals(expected[i])) {
    //System.out.println("Mismatch in position " + i + ", expected: " +
    //expected[i] + ", but got: " + input[i] + ".");
    //break;
    //}

    //junit testing
    assertArrayEquals(expected, input);

  }

  /**
   * Test the method of FindSmallest
   */
  @Test
  public void testFindSmallest() {
    String[] input = {"i", "have", "an", "egg"};
    int expected = 2;

    int actual = Sort.findSmallest(input, 0);
    assertEquals(expected, actual);

    String[] input2 = {"There", "are", "many", "pigs"};
    int expected2 = 2;

    int actual2 = Sort.findSmallest(input2, 2);
    assertEquals(expected2, actual2);
  }

  /**
   * Test the method of swap
   */
  @Test
  public void testSwap() {
    String[] input = {"i", "have", "an", "egg"};
    int a = 0;
    int b = 2;
    String[] expected = {"an", "have", "i", "egg"};

    Sort.swap(input, a, b);
    assertArrayEquals(expected, input);
  }
}
