package problem5;

public class DisruptorBuffer<E> {
	public static final int DEFAULT_CAPACITY = 64;
	
	private int capacity;
	private E[] data;
	private volatile int readSequence;
	private volatile int writeSequence;
	
	public DisruptorBuffer(int capacity) {
	    this.capacity = (capacity < 1) ? DEFAULT_CAPACITY : capacity;
	    this.data = (E[]) new Object[this.capacity];
	    this.readSequence = 0;
	    this.writeSequence = -1;
	}
	
	public boolean offer(E element) {
	    boolean isFull = (writeSequence - readSequence) + 1 == capacity;
	    if (!isFull) {
	        int nextWriteSeq = writeSequence + 1;
	        data[nextWriteSeq % capacity] = element;
	        writeSequence++;
	        return true;
	    }
	    return false;
	}
	
	public E poll() {
	    boolean isEmpty = writeSequence < readSequence;
	    if (!isEmpty) {
	        E nextValue = data[readSequence % capacity];
	        readSequence++;
	        return nextValue;
	    }
	    return null;
	}
}
