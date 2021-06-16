package problem6;

import java.util.Random;

public class ReadWriteLock{	
	private static final int MAXREADERS = 2;

	  private int readers       = 0;
	  private int writers       = 0;
	  private int writeRequests = 0;
	  private int readRequests  = 0;

	  public synchronized void lockRead() throws InterruptedException{
		readRequests++;  
		
	    while(writers > 0 || writeRequests > 0 || readers > MAXREADERS){
	      this.wait();
	    }
	    readRequests--;
	    readers++;
	  }

	  public synchronized void unlockRead(){
	    readers--;
	    this.notifyAll();
	  }

	  public synchronized void lockWrite() throws InterruptedException{
	    writeRequests++;
	    
	    while(readers > 0 || writers > 0){
	      this.wait();
	    }
	    writeRequests--;
	    writers++;
	  }

	  public synchronized void unlockWrite() throws InterruptedException{
	    writers--;
	    this.notifyAll();
	  }
}
