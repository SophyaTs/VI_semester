package problem8;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

import sun.misc.Unsafe;

public class NonBlockingQueue {
	static public class Node {
        public int value;
        public volatile Node next = null;
        protected long offset;
        protected Unsafe unsafe;

        Node(int value, Node next) throws NoSuchFieldException, SecurityException, IllegalAccessException {
            this.value = value;
            this.next = next;
            unsafe = getUnsafe();
            offset = unsafe.objectFieldOffset(Node.class.getDeclaredField("next"));
        }
        public String toString() {
            return Integer.toString(value);
        }
    }
	
	private static Unsafe getUnsafe() throws IllegalAccessException, NoSuchFieldException {
		Field f = Unsafe.class.getDeclaredField("theUnsafe");
	    f.setAccessible(true);
	    return (Unsafe) f.get(null);
	}
	
	private Node dummy;
    private volatile Node head;
    private volatile Node tail;
    private long offsetHead;
    private long offsetTail;
    private Unsafe unsafe;
    
    public NonBlockingQueue() throws NoSuchFieldException, SecurityException, IllegalAccessException {
    	dummy = new Node(-1000, null);
    	head = dummy;
    	tail = dummy;
    	unsafe = getUnsafe();
    	offsetHead = unsafe.objectFieldOffset(NonBlockingQueue.class.getDeclaredField("head"));
    	offsetTail = unsafe.objectFieldOffset(NonBlockingQueue.class.getDeclaredField("tail"));
    }
    
    public Node getTail() {
        return tail;
    }
    
    public void enqueue(int value) throws NoSuchFieldException, SecurityException, IllegalAccessException {
        Node new_node = new Node(value, null);
        while (true) {
            Node currentTail = tail;
            Node tailNext = currentTail.next;
            if (currentTail == tail) {
                if (tailNext != null) {
                	unsafe.compareAndSwapObject(this, offsetTail, currentTail, tailNext);
                    //tail.compareAndSet(currentTail, tailNext);
                } else {
                    if (currentTail.unsafe.compareAndSwapObject(currentTail,currentTail.offset, null, new_node)){
                        unsafe.compareAndSwapObject(this, offsetTail, currentTail, new_node);
                        return;
                    }
                }
            }
        }
    }

    public boolean dequeue() {
    	if(head == dummy) {
    		unsafe.compareAndSwapObject(this, offsetHead,dummy,dummy.next);
    	}
        while (true) {
            Node first = head;
            Node last = tail;
            Node next = first.next;
            if (first == head) {
                if (first == last) {
                    if (next == null) 
                    	return false;
                    //tail.compareAndSet(last, next);
                    unsafe.compareAndSwapObject(this, offsetTail,last,next);
                } else {
                    if (unsafe.compareAndSwapObject(this, offsetHead,first, next))
                        return true;
                }
            }
        }
    }

    public String toString() {
        StringBuilder answer = new StringBuilder();
        Node tmp = head;
        int counter = 0;
        while (tmp != null) {
            ++counter;
            if(tmp.value!=-1000) {
            	answer.append(tmp.value);
            if(tmp.next != null) {
                answer.append(' ');
            }}           
            tmp = tmp.next;
        }
        return answer.toString();
    }
}