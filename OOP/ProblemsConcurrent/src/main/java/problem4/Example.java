package problem4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Example {
	public static Random random = new Random();
	public static CyclicBarrier cb;
	
	public static class Task implements Runnable{
		private SkipList list;
		private List<Integer> values = new ArrayList<Integer>();
		
		public Task(){}
		public Task(SkipList list){
			this.list = list;
		}
		
		@Override
		public void run() {
			for(int i=0;i<10;++i) {
				int v = random.nextInt(100);
				list.add(v);
				values.add(v);
			}
			
			try {
				cb.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0;i<5;++i) {
				list.remove(values.get(random.nextInt(10)));
			}
			
		}
	}
	
	public static class Print implements Runnable{
		private SkipList list;
		
		public Print(SkipList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			System.out.println("Size "+list.size.get());
			System.out.println(list);			
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		SkipList list = new SkipList(5);
		Thread[] th = new Thread[3];
		
		cb = new CyclicBarrier(3, new Print(list));
		
		for(int i=0;i<3;++i) {
			th[i] = new Thread(new Task(list));
			th[i].start();
		}		
		for(int i=0;i<3;++i) {
			th[i].join();
		}
		System.out.println("Size "+list.size.get());
		System.out.println(list);		
	}
}
