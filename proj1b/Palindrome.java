import java.util.LinkedList;

public class Palindrome {

  /**
   * Given a String, wordToDeque should return a Deque where the characters appear in the same order
   * as in the String. For example, if the word is “persiflage”, then the returned Deque should have
   * ‘p’ at the front, followed by ‘e’, and so forth.
   *
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
   * helper method, return if the first and last characters of the deque are the same.
   */
  private boolean isPalindrome(Deque<Character> word) {
    if (word.size() < 2) {
      return true;
    }
    if (word.removeFirst() != word.removeLast()) {
      return false;
    }
    return isPalindrome(word);
  }

  /**
   * 判断是否为回文数
   *
   * @param word words
   * @return boolean
   */
  public boolean isPalindrome(String word) {
    Deque<Character> a = wordToDeque(word);
    return isPalindrome(a);
  }

  /**
   * Off by one helper method, return ture if the first and last character of the deque are
   * offByOne
   */

  private boolean isPalindrome(Deque<Character> word, CharacterComparator cc) {
    if (word.size() < 2) {
      return true;
    }
    if (!cc.equalChars(word.removeFirst(), word.removeLast())) {
      return false;
    }
    return isPalindrome(word, cc);
  }

  /**
   * Off by one, the method will return true if the word is a palindrome according to the character
   * comparison test provided by the CharacterComparator passed in as argument cc.
   *
   * @param cc 字符比较参数
   */
  public boolean isPalindrome(String word, CharacterComparator cc) {
    Deque<Character> a = wordToDeque(word);
    return isPalindrome(a, cc);
  }
}

