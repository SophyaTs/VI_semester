package lab4b;

public class ReadWriteLock{

	  private int readers       = 0;
	  private int writers       = 0;
	  private int writeRequests = 0;

	  public synchronized void lockRead() throws InterruptedException{
	    while(writers > 0 || writeRequests > 0){
	      this.wait();
	    }
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
