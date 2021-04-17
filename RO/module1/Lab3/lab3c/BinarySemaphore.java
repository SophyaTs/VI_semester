package lab3c;

public class BinarySemaphore {
	private boolean free = true;
	
	public synchronized void acquire() {
		if(free)
			free = false;
		else
			try {
				this.wait();
			} catch (InterruptedException e) {
				free = true;
				return;
			}
	}
	
	public synchronized void release() {
		free = true;
		this.notify();
	}
}
