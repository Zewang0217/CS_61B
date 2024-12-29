public class SLList {
    public class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;
    private int size;

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }    

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return first.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        /* Your Code Here! */
        IntNode p = first;
        while (p.next != null){
            p = p.next;
        }
        p.item = x;
    }

    /** Returns the number of items in the list using recursion. */
    public static int size(IntNode x){
        if(x.next == null){
            return 1;
        }
        return 1 + size(x.next);
    }
    public int size() {
        /* Your Code Here! */
        return size(first);
    }

}
