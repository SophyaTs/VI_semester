package problem8;

public class Example {
	
	public static class Worker implements Runnable{
		int a;
		
		public Worker(int a) {
			this.a = a;
		}
		
		public void run() {			
			try {
				for(int i = a; i< a+10; i++) {
					q.enqueue(i);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	static NonBlockingQueue q;
	static final int N = 5;
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalAccessException, InterruptedException {
		q = new NonBlockingQueue();
		
		Thread[] t = new Thread[N];
		for(int i=0; i<N; i++) {
			t[i] = new Thread(new Worker(i*10));
			t[i].start();
		}
		
		for(int i=0; i<N; i++){
			t[i].join();
		}
		
		System.out.println(q.toString());
		
		for(int i=0; i<N; i++) {
			t[i] = new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	for(int i=0; i<5;i++)
			    		//System.out.println(q.dequeue());
			    		q.dequeue();
			    }
			});
			t[i].start();
		}
		for(int i=0; i<N; i++){
			t[i].join();
		}
		
		System.out.println(q.toString());
		
		
//		for (int i = 1; i <=50; i++) {
//			q.enqueue(i);
//		}
//		System.out.println(q.toString());
//		for(int i =0; i<25; ++i) {
//			System.out.println(q.dequeue());
//			System.out.println(q.toString());
//			System.out.println();
//		}
		
	}
	

}
