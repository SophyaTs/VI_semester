package problem7;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Example {
	public static class Worker implements Runnable{
		private String s;
		
		public Worker(String s) {
			super();
			this.s = s;
		}
		
		@Override
		public void run() {
			System.out.println("Executed at "+(new Date())+": "+s);
		}
		
	}
	
	public static void main(String[] args) throws ParseException, InterruptedException {
		var sch = new Scheduler();
		sch.start();
		sch.scheduleRepeat(new Worker("repeat 2s"), 2000);
		System.out.println("Current time "+(new Date()));
		sch.sсhedule(new Worker("delay"), 5000);
		SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = sdf.parse("23-04-2021 21:29:00");
		sch.sсhedule(new Worker("date"), date);
		
		
		Thread.sleep(300*1000);
		sch.stop();
	}

}
