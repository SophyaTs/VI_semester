package lab6a;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SimuThread extends Thread {
    CyclicBarrier barrier;
    private Simulation simulation;
    private int start;
    private int task_size;

    SimuThread(CyclicBarrier barrier, Simulation simulation, int start, int task_size) {
        this.barrier = barrier;
        this.simulation = simulation;
        this.start = start;
        this.task_size = task_size;
    }

    private boolean insideField(int i, int j, Integer[][] field) {
        return i >= start && i < start + task_size && j >= 0 && j < field[0].length;
    }

    private int countAlliedNeighbors(int i, int j, Integer[][] field) {
        int answer = 0;
        int shifts[] = { -1, 0, 1 };
        for (int shift_x : shifts) {
            for (int shift_y : shifts) {
                if (shift_x == 0 && shift_y == 0)
                    continue;
                int x = i + shift_x;
                int y = j + shift_y;
                if (insideField(x, y, field)) {
                    if (field[x][y] == 1) {
                        ++answer;
                    }
                }
            }
        }
        return answer;
    }

    @Override
    public void run() {
        while (true) {
            while (!Manager.updated){
                Thread.yield();
            }
            Integer[][] field = simulation.getField();
            Integer[][] new_field = new Integer[task_size][Manager.COLUMNS];
            for (int i = start; i < start + task_size; ++i) {
                for (int j = 0; j < Manager.COLUMNS; ++j) {
                    new_field[i - start][j] = field[i][j];
                    int neighbors_number = countAlliedNeighbors(i, j, field);

                    if (field[i][j] == 0) {
                        if (neighbors_number == 3) {
                            new_field[i - start][j] = 1;
                        }
                    } else {
                        if (neighbors_number < 2 || neighbors_number > 3) {
                            new_field[i - start][j] = 0;
                        }
                    }
                }
            }
            for (int i = start; i < start + task_size; ++i) {
                for (int j = 0; j < Manager.COLUMNS; ++j) {
                    field[i][j] = new_field[i - start][j];
                }
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
