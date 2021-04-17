package lab6a;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Simulation implements Runnable {
    private Integer[][] field;
    Buffer buffer;
    CyclicBarrier barrier;

    Integer[][] getField() {
        return field;
    }

    Simulation(Buffer buffer, CyclicBarrier barrier, int row_number, int column_number) {
        this.buffer = buffer;
        this.barrier = barrier;
        field = new Integer[row_number][column_number];
        for (int i = 0; i < row_number; ++i) {
            Random random = new Random();
            for (int j = 0; j < column_number; ++j) {
                field[i][j] = Math.abs(random.nextInt() % (Manager.CIVILIZATION_NUMBER + 1));
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // update();
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            buffer.putInSecondary(field);
            Manager.updated = true;
        }
    }
}
