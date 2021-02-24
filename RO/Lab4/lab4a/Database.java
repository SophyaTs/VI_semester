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
	
	public static class FinderPhone implements Runnable{

		public void run() {
			while(!Thread.currentThread().isInterrupted())
				try {
					StringBuilder strb = new StringBuilder();
					for(int i = 0; i < 13; ++i)
						strb.append(rand.nextInt(10));
					String expectedNumber = strb.toString();
					
					ReadLock l = lock.readLock();
					l.lock();
					Scanner sc = new Scanner(new File(file));
					while(sc.hasNextLine()) {
						String line = sc.nextLine();
						String number = line.substring(0, 13);
						String name = line.substring(14,line.length());
						
						if(expectedNumber == number ) {
							System.out.println("Found phone number!");
							break;
						} else						
							System.out.println("Phone number not found!");
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
					StringBuilder strb = new StringBuilder();
					for(int i = 0; i < 10; ++i)
						strb.append((char)(65+rand.nextInt(36)));
					String expectedName = strb.toString();
					
					ReadLock l = lock.readLock();
					l.lock();
					Scanner sc = new Scanner(new File(file));
					while(sc.hasNextLine()) {
						String line = sc.nextLine();
						String number = line.substring(0, 13);
						String name = line.substring(14,line.length());
						
						if(expectedName == name ) {
							System.out.println("Found name!");
							break;
						} else						
							System.out.println("Name not found!");
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
					StringBuilder strb = new StringBuilder();
					for(int i = 0; i < 13; ++i)
						strb.append(rand.nextInt(10));
					strb.append(" ");
					for(int i = 0; i < 10; ++i)
						strb.append((char)(65+rand.nextInt(36)));
					String line = strb.toString();
					
					WriteLock l = lock.writeLock();
					l.lock();
					FileWriter fw = new FileWriter(file, true);
					fw.write(line+"\n");
					System.out.println("Added "+line);
				    fw.close();
				    l.unlock();
				    
				    randomSleep();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    
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
