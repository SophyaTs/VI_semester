package lab2a;

import java.util.Arrays;
import java.util.Random;

public class Manager {
	public final int SIZE = 100;
	public final int NUMBER_OF_AREAS = 10;
	public final int AREA_SIZE;
	public final int SWARMS = 4;
	
	public Boolean found = false;
	public int finished = 0;
	public final Object lock = new Object();
	
	private ThreadPool swarms;
	private int[][] field;
	private int nextReg = 0;
	
	public Manager() {
		AREA_SIZE = SIZE/NUMBER_OF_AREAS;
		field = new int[SIZE][SIZE];
		for(int[] a: field)
			Arrays.fill(a,0);    
		
		Random rand = new Random();
		int x = rand.nextInt(SIZE);
		int y = rand.nextInt(SIZE);
		field[x][y] = 1;
				
		System.out.printf("Generated at %d %d\n", x,y);
	}
	
	public void manage() throws InterruptedException {
		swarms = new ThreadPool(SWARMS);
		for(int i=0; i<SWARMS; ++i)
			createTask();
		
		while(true) {
			synchronized(lock) {
				if(!found && (finished == 0))
					lock.wait();
				
				if(!found)
					while(finished>0) {
						createTask();
						--finished;
					}
				else
					break;
			}
		}
		
		swarms.stop();
	}
	
	private void createTask() {
		swarms.execute(new BeeSwarm(this, field, nextReg));
		++nextReg;		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Manager m = new Manager();
		m.manage();
	}

}
