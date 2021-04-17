package lab4b;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Garden {
	public static final int N = 10;
	private static final String file = "src/main/java/lab4b/output_4b.txt"; 
	private static Random rand = new Random();
	
	// 0 - watered, 1 - not watered
	private static int[][] field = new int[N][N];
	private static int x = 0;
	private static int y = 0;
	
	private static ReadWriteLock lock = new ReadWriteLock();
	
	public static class Gardener implements Runnable{
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					lock.lockWrite();					
					field[x][y] = 0;
					System.out.println("Watered " + x + "," + y);
					lock.unlockWrite();
				} catch (InterruptedException e) {
					return;
				}
				randomSleep();
			}
		}
		
	}
	
	public static class Nature implements Runnable{
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					lock.lockWrite();
					x = rand.nextInt(N);
					y = rand.nextInt(N);
					field[x][y] = 1;
					System.out.println(x + "," + y + " is now dry!");
					lock.unlockWrite();
				} catch (InterruptedException e) {
					return;
				}				
				randomSleep();
			}
		}
		
	}
	
	public static class Monitor1 implements Runnable{ // writes to file
		public void run() {
			FileWriter fw = null;
			try {
				fw = new FileWriter(file);
				
				while(!Thread.currentThread().isInterrupted()) {
					try {
						lock.lockRead();
						String str = printField();
						lock.unlockRead();						
						fw.write(str+"\n");						
					} catch (InterruptedException e) {
						fw.close();
						return;					
					}
					randomSleep();
				}
				fw.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
		}
		
	}
	
	public static class Monitor2 implements Runnable{ // writes to terminal
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					lock.lockRead();
					System.out.println(printField());
					System.out.println();
					lock.unlockRead();
				} catch (InterruptedException e) {
					return;
				}
				randomSleep();
			}
		}
		
	}
	
	public static String printField() {
		StringBuilder strb = new StringBuilder();
		for(int[] a: field) {			
			for(int i: a) {
				strb.append(i);
				strb.append(' ');
			}
			strb.append('\n');			
		}
		return strb.toString();
	}
	
	public static void randomSleep() {
		try {
			Thread.sleep(rand.nextInt(4)*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Gardener());
		Thread t2 = new Thread(new Nature());
		Thread t3 = new Thread(new Monitor1());
		Thread t4 = new Thread(new Monitor2());
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		Thread.sleep(10*1000);
		
		t1.interrupt();
		t2.interrupt();
		t3.interrupt();
		t4.interrupt();
	}

}
