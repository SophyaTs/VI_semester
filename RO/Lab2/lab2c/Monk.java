package lab2c;

import java.util.Random;

public class Monk {
	public final int energyTsi;
	public final Monastery monastery;
	
	public Monk(Monastery monastery) {
		this.monastery = monastery;		
		Random rand = new Random();
		energyTsi = rand.nextInt(1000);
	}
		
	public static enum Monastery{
		GuanIn, GuanYan
	}
	
	@Override
	public String toString() {
		return(monastery.toString() + " " + energyTsi);
	}

}
