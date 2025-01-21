/**
 * Array based list.
 *
 * @author Josh Hug
 */

public class AList<Item> {
  private Item[] item;
  private int size;

  /**
   * Creates an empty list.
   */
  public AList() {
    item = (Item[]) new Object[100];
    size = 0;
  }

  /**
   * Resize the underlying array to the target capacity
   */
  private void resize(int capacity) {
    Item[] newItem =(Item[]) new Object[capacity];
    System.arraycopy(item, 0, newItem, 0, size);
    item = newItem;
  }

  /**
   * Inserts X into the back of the list.
   */
  public void addLast(Item x) {
    if (size == item.length) {
      resize(size * 2);
    }
    item[size] = x;
    size++;
  }

  /**
   * Returns the item from the back of the list.
   */
  public Item getLast() {
    return item[size - 1];
  }

  /**
   * Gets the ith item in the list (0 is the front).
   */
  public Item get(int i) {
    return item[i];
  }

  /**
   * Returns the number of items in the list.
   */
  public int size() {

    return size;
  }

  /**
   * Deletes item from back of the list and
   * returns deleted item.
   */
  public Item removeLast() {
    Item last = item[size - 1];
    item[size - 1] = null;
    size--;
    return last;
  }
} 