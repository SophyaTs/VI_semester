package lab4a;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Database {
	private static String file = "src/main/java/lab4a/input_4a.txt"; 
	private static Random rand = new Random();
	
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static String lastName;
	private static String lastNumber;
	
	public static class FinderPhone implements Runnable{

		public void run() {
			while(!Thread.currentThread().isInterrupted())
				try {					
					ReadLock l = lock.readLock();
					l.lock();
					System.out.println("Looking for Phone number...");
					Scanner sc = new Scanner(new File(file));
					while(sc.hasNextLine()) {
						String line = sc.nextLine();
						String number = line.substring(0, 13);
						String name = line.substring(14,line.length());
						
						if(number.equals(lastNumber) ) {
							System.out.println("Found phone number!");
							break;
						}
					}
					sc.close();
					l.unlock();
					
					randomSleep();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}

	public static class FinderName implements Runnable{

		public void run() {
			while(!Thread.currentThread().isInterrupted())
				try {					
					ReadLock l = lock.readLock();
					l.lock();
					System.out.println("Looking for Name...");
					Scanner sc = new Scanner(new File(file));
					while(sc.hasNextLine()) {
						String line = sc.nextLine();
						String number = line.substring(0, 13);
						String name = line.substring(14,line.length());
						
						if(name.equals(lastName)) {
							System.out.println("Found name!");
							break;
						}												
					}
					sc.close();
					l.unlock();
					
					randomSleep();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	public static class Editor implements Runnable{

		public void run() {
			while(!Thread.currentThread().isInterrupted())
				try {
									
					
					WriteLock l = lock.writeLock();
					l.lock();
					if(rand.nextBoolean())
						add();
					else
						remove();
				    l.unlock();
				    
				    randomSleep();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    
		}
		
		private void add() throws IOException {
			StringBuilder strbNumber = new StringBuilder();
			StringBuilder strbName = new StringBuilder();
			for(int i = 0; i < 13; ++i)
				strbNumber.append(rand.nextInt(10));					
			for(int i = 0; i < 10; ++i)
				strbName.append((char)(65+rand.nextInt(36)));
			
			FileWriter fw = new FileWriter(file, true);
			lastNumber = strbNumber.toString();
			lastName = strbName.toString();
			String line = lastNumber + " " + lastName;
			fw.write(line+"\n");
			System.out.println("Added "+line);
		    fw.close();
		}
		
		private void remove() throws IOException {
			Scanner sc = new Scanner(new File(file));
			sc.nextLine();
			
			StringBuilder strb = new StringBuilder();
			while(sc.hasNextLine())
				strb.append(sc.nextLine()+"\n");
			sc.close();
			FileWriter fw = new FileWriter(file);
			fw.write(strb.toString());
			fw.close();
			System.out.println("Removed record");
		}
		
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
		Thread t1 = new Thread(new FinderPhone());
		Thread t2 = new Thread(new FinderName());
		Thread t3 = new Thread(new Editor());

		t1.start();
		t2.start();
		t3.start();
		
		Thread.sleep(10000);
		
		t1.interrupt();
		t2.interrupt();
		t3.interrupt();
		
		t1.join();
		t2.join();
		t3.join();
	}

}
