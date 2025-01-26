/**
 * 这是CS_61B的双向队列的project. 支持在队列的头部和尾部进行添加和删除操作.
 *
 * @param <T> 队列中存储的数据类型
 * @author Zewang
 * @version 1.0
 * @since 2025-1-2
 */

public class LinkedListDeque<T> {

  /**
   * 链表中的节点类.
   */
  public class Node {

    private Node pre;
    private Node next;
    private T item;

    /**
     * 创建一个新的节点.
     *
     * @param item 存储的数据，类型为 T
     * @param pre  前一个节点
     * @param next 后一个节点
     */
    public Node(T item, Node pre, Node next) {
      this.item = item;
      this.pre = pre;
      this.next = next;
    }
  }

  /**
   * 哨兵节点，用于简化边界条件处理，得以实现常数复杂度的添加删除.
   */
  private final Node sentinel;
  private int size;

  /**
   * 构造一个空的双端队列.
   */
  public LinkedListDeque() {
    sentinel = new Node(null, null, null);
    size = 0;
  }

  /**
   * 构造一个包含单个元素的双端队列.
   *
   * @param item 队列的第一个元素
   */
  public LinkedListDeque(T item) {
    sentinel = new Node(null, null, null);
    sentinel.next = new Node(item, sentinel, null);
    size = 1;
  }

  /**
   * 在队列头部添加一个元素.
   *
   * @param item 要添加的元素
   */
  public void addFirst(T item) {
    Node temp = new Node(item, null, null);
    if (size > 0) {
      sentinel.next.pre = temp;
      temp.next = sentinel.next;
    }
    sentinel.next = temp;
    temp.pre = sentinel;
    size++;
  }

  /**
   * get recursive.
   */
  private T getRecursive(int index, Node currentSentinel) {
    if (index >= size) {
      return null;
    }
    if (index == 0) {
      return currentSentinel.next.item;
    }
    return getRecursive(index, sentinel);
  }

  public T getRecursive(int index) {
    return getRecursive(index, sentinel);
  }

  /**
   * 在队列尾部添加一个元素.
   *
   * @param item 尾部要添加的元素
   */
  public void addLast(T item) {
    Node p = sentinel;
    while (p.next != null) {
      p = p.next;
    }
    p.next = new Node(item, p, null);
    size++;
  }

  /**
   * 检查队列是否为空.
   *
   * @return 如果队列为空返回true，否则返回false
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * 获取队列中元素数量.
   *
   * @return 队列中的元素数量
   */
  public int size() {
    return size;
  }

  /**
   * 打印队列中的所有元素.
   */
  public void printDeque() {
    Node p = sentinel.next;
    while (p != null) {
      System.out.println(p.item);
      p = p.next;
    }
  }

  /**
   * 从队列头部移除一个元素.
   *
   * @return 被移除的元素，若队列为空则返回null
   */
  public T removeFirst() {
    if (size == 0) {
      return null;
    }
    T item = sentinel.next.item;
    if (sentinel.next.next != null) {
      sentinel.next.next.pre = sentinel;
    }
    sentinel.next = sentinel.next.next;  // 更新 sentinel.next
    size--;
    return item;
  }

  /**
   * 从队列尾部移除一个元素.
   *
   * @return 被移除的元素，若队列为空则返回null
   */
  public T removeLast() {
    if (size == 0) {
      return null;
    }
    Node p = sentinel;
    while (p.next != null) {
      p = p.next;
    }
    T item = p.item;
    p.pre.next = null;
    size--;
    return item;
  }

  /**
   * 获取队列中指定位置的元素.
   *
   * @param index 要获取的元素的索引
   * @return 指定位置的元素，若索引无效会队列为空则返回null
   */
  public T get(int index) {
    Node p = sentinel;
    if (index < 0 || index >= size) {
      return null;
    }
    for (int i = 0; i < index; i++) {
      p = p.next;
    }
    return p.next.item;
  }
}
