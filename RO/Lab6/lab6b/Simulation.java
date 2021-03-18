package lab6b;

import java.util.Random;

public class Simulation implements Runnable {
    private Integer[][] field;
    private int row_number;
    private int column_number;
    Buffer buffer;
    
    Simulation(Buffer buffer, int row_number, int column_number) {
        this.buffer = buffer;
        this.row_number = row_number;
        this.column_number = column_number;
        field = new Integer[row_number][column_number];
        for (int i = 0; i < row_number; ++i) {
            Random random = new Random();
            for (int j = 0; j < column_number; ++j) {
                field[i][j] = Math.abs(random.nextInt() % Manager.CIVILIZATION_NUMBER + 1);
            }
        }
    }

    private boolean insideField(int i, int j) {
        return i >= 0 && i < field.length && j >= 0 && j < field[0].length;
    }

    private int countAlliedNeighbors(int i, int j) {
        int answer = 0;
        int shifts[] = { -1, 0, 1 };
        for (int shift_x : shifts) {
            for (int shift_y : shifts) {
                if (shift_x == 0 && shift_y == 0)
                    continue;
                int x = i + shift_x;
                int y = j + shift_y;
                if (insideField(x, y)) {
                    if (field[x][y] == field[i][j]) {
                        ++answer;
                    }
                }
            }
        }
        return answer;
    }

    private Integer[] countAllNeighbors(int i, int j) {
        Integer neighbors[] = new Integer[Manager.CIVILIZATION_NUMBER];
        for (int k = 0; k < neighbors.length; ++k) {
            neighbors[k] = 0;
        }
        int shifts[] = { -1, 0, 1 };
        for (int shift_x : shifts) {
            for (int shift_y : shifts) {
                if (shift_x == 0 && shift_y == 0)
                    continue;
                int x = i + shift_x;
                int y = j + shift_y;
                if (insideField(x, y)) {
                    if (field[x][y] != 0) {
                        ++neighbors[field[x][y] - 1];
                    }
                }
            }
        }
        return neighbors;
    }

    public void update() {
        Integer[][] new_field = new Integer[row_number][column_number];
        for (int i = 0; i < row_number; ++i) {
            for (int j = 0; j < column_number; ++j) {
                new_field[i][j] = field[i][j];

                if (field[i][j] == 0) {
                    Integer[] neighbors = countAllNeighbors(i, j);
                    int civil = -1;
                    int neighb_num = 0;
                    for (int k = 0; k < neighbors.length; ++k) {
                        if (neighbors[k] > neighb_num) {
                            neighb_num = neighbors[k];
                            civil = k;
                        }
                    }
                    if (civil != -1 && neighb_num == 3) {
                        new_field[i][j] = civil + 1;
                    }
                } else {
                    int neighbors_number = countAlliedNeighbors(i, j);
                    if (neighbors_number < 2 || neighbors_number > 3) {
                        new_field[i][j] = 0;
                    }
                }
            }
        }
        field = new_field;
        // for (int i = 0; i < field.length; ++i)
        // {
        // for (int j = 0; j < field[0].length; ++j)
        // {
        // System.out.print(field[i][j] + " ");
        // }
        // System.out.println();
        // }
    }

    @Override
    public void run() {
        while (true) {
            buffer.putInSecondary(field);
            update();
        }
    }
}