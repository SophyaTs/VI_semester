package lab3a;

public class Forest {
	public static final int N = 50;
	public static final int n = 4;
	
	private static Integer honeyPot = 0;
	private static Object lock = new Object();
	
	public static class Bee implements Runnable{		
		
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				synchronized(lock) {
					if(honeyPot < N) {					
						++honeyPot;
						System.out.printf("BzzzzBzzz new Honey = %d\n", honeyPot);
	
						if(honeyPot == N)
							lock.notify();
					}
				}			
			}
		
		}
	}
	
	public static class WinnieThePooh implements Runnable {

		public void run() {
			while(!Thread.currentThread().isInterrupted()) {								
				synchronized(lock) {
					if (honeyPot < N) {
						try {
							lock.wait();
						}  catch (InterruptedException e) {
							return;
						}
					} else {
						System.out.printf("Pooh eats Honey = %d\n", honeyPot);
						honeyPot = 0;
					}
				}
			}
		}			
	}
		

	public static void main(String[] args) throws InterruptedException {
		Thread Pooh = new Thread(new WinnieThePooh());
		Pooh.start();
		
		Thread[] bees = new Thread[n];
		for(Thread t: bees) {
			t = new Thread(new Bee());
			t.start();
		}
		
		Thread.sleep(5000);
		
		for(Thread t: bees) {
			t.interrupt();
		}
		Pooh.interrupt();
	}

}
