import java.util.LinkedList;

public class Palindrome {
  /**
   * Given a String, wordToDeque should return a Deque where the characters
   * appear in the same order as in the String.
   * For example, if the word is “persiflage”, then the returned Deque
   * should have ‘p’ at the front, followed by ‘e’, and so forth.
   * @param word
   */
  public Deque<Character> wordToDeque(String word) {
    Deque<Character> a = new LinkedListDeque<>();
    //word.charAt 获取String的第i个char
    for (int i = 0; i < word.length(); i++) {
      a.addLast(word.charAt(i));
    }
    return a;
  }

  /**
   * 帮助判断回文数的私有方法
   */
  private String minus(String word) {
    String a = new String();
    int k = 0;
    for (int i = 1; i < word.length() - 1; i++) {
     a += word.charAt(i);
    }
    return a;
  }
  /**
   * 判断是否为回文数
   * @param word
   * @return boolean
   */
  public boolean isPalindrome(String word) {
    if (word.length() < 2) {
      return true;
    }
    if (word.charAt(0) != word.charAt(word.length() - 1)) {
      return false;
    }
    return isPalindrome(minus(word));
  }
}
