package lab3a;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Forest {
	public static final int N = 50;
	
	private static Integer honeyPot = 0;
	private static Semaphore s = new Semaphore(1);
	private static CyclicBarrier cb = new CyclicBarrier(N, new WinnieThePooh());
	
	public static class Bee implements Runnable{		
		
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {				
				try {
					s.acquire();
					++honeyPot;
					s.release();
					
					Random rand = new Random();
					float writeMessege = rand.nextFloat();
					if (writeMessege < 0.15)
						System.out.println("Bzzzzzzz! Bees are working");
					
					cb.await();
				} catch (InterruptedException e) {
					return;
				} catch (BrokenBarrierException e) {
					return;
				}
			}
		
		}
	}
	
	public static class WinnieThePooh implements Runnable {

		public void run() {
			try {
				s.acquire();
				System.out.printf("Pooh eats Honey = %d\n", honeyPot);
				honeyPot = 0;
				s.release();
			} catch (InterruptedException e) {
				return;
			}			
		}			
	}
		

	public static void main(String[] args) throws InterruptedException {				
		Thread[] bees = new Thread[N];
		for(int i =0; i < N; ++i) {
			bees[i] = new Thread(new Bee());
			bees[i].start();
		}
		
		Thread.sleep(5000);
		
		for(Thread t: bees) {
			t.interrupt();
		}
	}

}
