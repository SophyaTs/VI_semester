package lab6b;


import javax.swing.JFrame;

public class Manager {
    static final int CIVILIZATION_NUMBER = 3;
    private int cell_size;
    private int rows;
    private int columns;

    Manager(int cell_size, int rows, int columns) {
        this.cell_size = cell_size;
        this.rows = rows;
        this.columns = columns;
    }

    public void work() {
        Buffer buffer = new Buffer();
        Simulation simulation = new Simulation(buffer, rows, columns);
        Drawing drawing = new Drawing(buffer, cell_size);

        JFrame frame = new JFrame();
        frame.setSize((rows) * cell_size, (columns + 3) * cell_size);
        frame.getContentPane().add(drawing);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Thread draw = new Thread(drawing);
        Thread simulate = new Thread(simulation);
        simulate.start();
        draw.start();
        while (true) {
        }
    }
}
