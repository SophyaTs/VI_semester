package lab3c;

public class CyclicBarrier {
	private int numThreads = 2;
	private int finishedThreads = 0;	
	private Runnable commonRunnable = null;
	
	public CyclicBarrier() {
		super();
	}
	
	public CyclicBarrier(int numThreads) {
		super();
		this.numThreads = numThreads;
	}

	public CyclicBarrier(int numThreads, Runnable commonRunnable) {
		super();
		this.numThreads = numThreads;
		this.commonRunnable = commonRunnable;
	}

	public synchronized void await(){
		++finishedThreads;	
		
		try {
			if(finishedThreads==numThreads)
				this.overcomeBarrier();
			else {
				this.wait();
			}
		} catch (InterruptedException e) {
			return;
		}
	}
		
	private synchronized void overcomeBarrier(){
		if(commonRunnable != null) {
			Thread th = new Thread(commonRunnable);
			th.start();	
			try {
				th.join();
			} catch (InterruptedException e) {
				return;
			}
		}		
		finishedThreads = 0;
		
		//notify threads that they can continue
		this.notifyAll();
	}
}
