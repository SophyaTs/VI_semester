package lab5a;

public class MyCyclicBarrier {
    private final int initialParties;
    private int partiesAwait;

    public MyCyclicBarrier(int parties) {
        initialParties = parties;
        partiesAwait = parties;
    }

    public synchronized void await() throws InterruptedException {
        partiesAwait--;
        if (partiesAwait > 0) {
            this.wait();
        }

        //This makes it cyclic
        partiesAwait = initialParties;
        notifyAll();
    }
}
