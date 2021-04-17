package lab2a;

public class BeeSwarm implements Runnable{
	private Manager m;
	private final int[][] field;
	private final int region;
	
	public BeeSwarm(Manager m, int[][] field, int region) {
		super();
		this.m = m;
		this.field = field;
		this.region = region;
	}

	public void run() {
		for(int i = region*m.AREA_SIZE; i < region*m.AREA_SIZE + m.AREA_SIZE && i < m.SIZE; ++i)
			for(int j=0; j< m.SIZE; ++j) {
				//System.out.printf("%d %d\n",i,j);
				if(field[i][j] == 1) {
					synchronized(m.lock) {
						++m.finished;
						m.found = true;
						m.lock.notify();
						System.out.printf("Found at %d %d\n", i, j);
					}
				}				
			}
		synchronized(m.lock) {						
			++m.finished;
			m.lock.notify();
		}
		//System.out.printf("Not found\n");
	}

}
