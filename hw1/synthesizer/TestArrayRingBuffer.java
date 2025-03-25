package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEnqueueAndDequeue() {
        // 创建一个容量为 3 的 ArrayRingBuffer 对象
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        // 检查队列初始是否为空
        assertTrue(arb.isEmpty());
        // 入队元素 1
        arb.enqueue(1);
        // 入队元素 2
        arb.enqueue(2);
        // 入队元素 3
        arb.enqueue(3);
        // 检查队列是否已满
        assertTrue(arb.isFull());
        // 出队元素，检查是否为 1
        assertEquals((Integer) 1, arb.dequeue());
        // 出队元素，检查是否为 2
        assertEquals((Integer) 2, arb.dequeue());
        // 出队元素，检查是否为 3
        assertEquals((Integer) 3, arb.dequeue());
        // 检查队列是否为空
        assertTrue(arb.isEmpty());
    }

    @Test
    public void testPeek() {
        // 创建一个容量为 2 的 ArrayRingBuffer 对象
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(2);
        // 入队元素 10
        arb.enqueue(10);
        // 查看队首元素，检查是否为 10
        assertEquals((Integer) 10, arb.peek());
        // 入队元素 20
        arb.enqueue(20);
        // 查看队首元素，检查是否仍为 10
        assertEquals((Integer) 10, arb.peek());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
