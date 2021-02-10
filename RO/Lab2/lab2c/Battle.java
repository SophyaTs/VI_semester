package lab2c;

import java.util.concurrent.RecursiveTask;

public class Battle extends RecursiveTask<Integer> {
	public final static int ROUNDS = 3; 
	
	private Monk[] monks;
	private final int left;
	private final int right;
	
	public Battle(Monk[] monks, int left, int right) {
		this.left  = left;
		this.right = right;
		this.monks = monks;
		
	}
	
	@Override
	protected Integer compute() {
		if (right - left > 2) {            
            Integer middle = (left + right)/2;
            
            Battle battle1 = new Battle(monks, left, middle);
            battle1.fork();
            
            Battle battle2 = new Battle(monks, middle, right);            
            battle2.fork();
            
            Integer winner1 = battle1.join();
            Integer winner2 = battle2.join();
            return decideWinner(winner1, winner2);
        } else {
        	return decideWinner(left, left+1);
        }
	}
	
	private Integer decideWinner(Integer m1, Integer m2) {
		Integer winner = monks[m1].energyTsi > monks[m2].energyTsi ? m1 : m2;
		return winner;
	}
	
	public static void main(String[] args) {
		Monk[] monks = new Monk[(int) Math.pow(2,ROUNDS)];
		for(int i=0; i < monks.length; i+=2)
			monks[i] = new Monk(Monk.Monastery.GuanIn);				
		for(int i=1; i < monks.length; i+=2)
			monks[i] = new Monk(Monk.Monastery.GuanYan);
		
		for(int i=0; i < monks.length; i++)
			System.out.println(i+ " " + monks[i]);
		
		Battle tournament = new Battle(monks,0,monks.length - 1);
		Integer winner = tournament.compute();
		
		System.out.println("The Ultimate Winner is "+ winner+ " from " + monks[winner].monastery);
	}

}
