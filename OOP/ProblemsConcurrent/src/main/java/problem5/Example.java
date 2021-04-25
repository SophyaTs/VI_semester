package problem5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example {
	
	static DisruptorBuffer<String> buffer;
	
	public static class Producer implements Runnable {
		String[] items;
		
		public Producer(String[] items) {
			super();
			this.items = items;
		}

		public void run() {
		    for (int i = 0; i < items.length;) {
		        if (buffer.offer(items[i])) {
		           System.out.println("Produced: " + items[i]);
		            i++;
		        }
		    }
		}
	}
	
	public static class Consumer<T> implements Runnable {
		public void run() {
		    T[] items = (T[]) new Object[100];
		    for (int i = 0; i < items.length;) {
		        T item = (T) buffer.poll();
		        if (item != null) {
		            items[i++] = item;
		            System.out.println("Consumed: " + item);
		        }
		    }
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		buffer = new DisruptorBuffer<String>(5);
		
		String[] items = {
				"Triangle",
				"Square",
				"Rectangle",
				"Pentagon",
				"Hexagon",
				"Hectagon",
				"Octagon"
		};
		
		ExecutorService agent = Executors.newFixedThreadPool(2);
		agent.execute(new Producer(items));
		agent.execute(new Consumer<String>());
		Thread.sleep(10*1000);
	}
}
