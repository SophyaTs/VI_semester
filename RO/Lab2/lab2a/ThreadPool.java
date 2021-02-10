package lab2a;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadPool {

    private Queue<Runnable> taskQueue;
    private List<PoolThread> threads;
    private AtomicBoolean stopped = new AtomicBoolean(false);

    public ThreadPool(int noOfThreads){
        taskQueue = new ConcurrentLinkedQueue<Runnable>();
        threads = new ArrayList<PoolThread>();

        for(int i=0; i<noOfThreads; i++){
            threads.add(new PoolThread(this));
        }
        for(PoolThread thread : threads){
            thread.start();
        }
    }

    public synchronized void  execute(Runnable task) throws IllegalStateException{
        if(stopped.get()) throw
            new IllegalStateException("ThreadPool is stopped");

        this.taskQueue.add(task);
    }

    public synchronized void stop() throws InterruptedException{
        boolean success = this.stopped.compareAndSet(false, true);
        
        if(success)
        	for(PoolThread thread : threads) {
        		thread.join();
        	}
    }
    
    public static class PoolThread extends Thread {
    	private ThreadPool owner;
        private Queue<Runnable>taskQueue;

        public PoolThread(ThreadPool owner){
        	this.owner = owner;
            this.taskQueue = owner.taskQueue;
        }

        public void run(){
            while(true){
                Runnable runnable = (Runnable) taskQueue.poll();
                if(!owner.stopped.get()) {
                	if(runnable != null) {
                		System.out.println("New Task");
                		runnable.run();
                	}
                } else
                	return;              	
            }
        }
    }

}