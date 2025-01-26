import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
  static OffByN OffBy3 = new OffByN(3);
  static OffByN OffBy5 = new OffByN(5);
  @Test
  public void TestOffByNN() {
    assertTrue(OffBy3.equalChars('a','d'));
    assertTrue(OffBy5.equalChars('f', 'a'));
    assertFalse(OffBy3.equalChars('a','h'));
  }
}
