package lab5b;

import java.util.concurrent.CyclicBarrier;

public class Application {

    private static final StringBuilder[] stringBuilders = new StringBuilder[4];

    private static final Thread[] threads = new Thread[4];
    
    public static StringBuilder[] getStringBuilders() {
		return stringBuilders;
	}

	public static Thread[] getThreads() {
		return threads;
	}



	public static void main(String[] args) {
        stringBuilders[0] = new StringBuilder("ABAAAAABBBBCD");
        stringBuilders[1] = new StringBuilder("ABAAAAAAABCCDD");
        stringBuilders[2] = new StringBuilder("AACAABCD");
        stringBuilders[3] = new StringBuilder("ACCAACBCBCD");

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new CheckerRunnable());

        for(int i =0; i<threads.length;i++){
            threads[i] = new Thread(new StringRunnable(i,cyclicBarrier));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

}
