package problem6;

import java.util.Random;

public class Example {
	static ReadWriteLock lock = new ReadWriteLock();
	static int item = 0;
	
	static Random rand = new Random();
	
	static final int READERS = 5;
	static final int WRITERS = 3;
	
	public static void main(String[] args) {		
		Thread[] readers = new Thread[READERS];
		for(int i = 0; i < READERS; ++i) {
			readers[i] = new Thread( new Runnable() {
				public void run() {
					while(true) {
						try {
							lock.lockRead();
							System.out.println("Reading Thread "+Thread.currentThread().getId()+" read the value "+item+" and goes to sleep");
							Thread.sleep(3000);
							lock.unlockRead();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
			});
			readers[i].start();
		}
		
		Thread[] writers = new Thread[WRITERS];
		for(int i = 0; i < WRITERS; ++i) {
			writers[i] = new Thread(new Runnable() {
				public void run() {
					while(true) {
						try {
							lock.lockWrite();
							item++;
							System.out.println("Writing Thread "+Thread.currentThread().getId()+" wrote the value "+item);							
							lock.unlockWrite();
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			writers[i].start();
		}
	}
}
