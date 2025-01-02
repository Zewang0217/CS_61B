public class LinkedListDeque<T> {
    public class Node {
       public Node pre;
       public Node next;
       public T item;
       public Node(T item, Node pre, Node next) {
           this.item = item;
           this.pre = pre;
           this.next = next;
       }
    }
    private Node sentinel;
    private int size;
    //初始化完成

    //两个构造方法
    public LinkedListDeque() {
        sentinel = new Node(null,null,null);
        size = 0;
    }
    public  LinkedListDeque(T item) {
        sentinel = new Node(null,null,null);
        sentinel.next = new Node(item,sentinel,null);
        size = 1;
    }
    public void addFirst(T item) {
        Node temp = new Node(item,null,null);
        if (size > 0) {
            sentinel.next.pre = temp;
            temp.next = sentinel.next;
        }
        sentinel.next = temp;
        temp.pre = sentinel;
        size++;
    }
    public void addLast(T item) {
        Node p = sentinel;
        while (p.next != null){
            p = p.next;
        }
        p.next = new Node(item,p,null);
        size++;
    }
    public boolean isEmpty() {
        if (size == 0) return true;
        else return false;
    }
    public int size() {
        return size;
    }
    public  void printDeque() {
        Node p = sentinel.next;
        while (p != null){
            System.out.println(p.item);
            p = p.next;
        }
    }
    public T removeFirst() {
        if (size == 0) return null;
        T item = sentinel.next.item;
        if (sentinel.next.next != null) {
            sentinel.next.next.pre = sentinel;
        }
        sentinel.next = sentinel.next.next;  // 更新 sentinel.next
        size--;
        return item;
    }

    public T removeLast() {
        if (size == 0) return null;
        Node p = sentinel;
        while (p.next != null){
            p = p.next;
        }
        T item = p.item;
        p.pre.next = null;
        size--;
        return item;
    }
    public T get(int index) {
        Node p = sentinel;
        if (index < 0 || index >= size || size == 0) return null;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.next.item;
    }
}
