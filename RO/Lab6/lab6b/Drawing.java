package lab6b;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Drawing extends JPanel implements Runnable {
    private int cell_size;
    Integer[][] field;
    Buffer buffer;
    private Color[] colors = { Color.BLACK, Color.WHITE, Color.YELLOW, Color.RED, Color.PINK };

    Drawing(Buffer buffer, int cell_size) {
        this.cell_size = cell_size;
        this.buffer = buffer;
    }

    @Override
    public void paint(Graphics g) {
        if (field == null)
            return;
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                Color color = colors[field[i][j]];
                g.setColor(color);
                g.fillRect(i * cell_size, j * cell_size, (i + 1) * cell_size, (j + 1) * cell_size);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            field = buffer.getFromPrimary();
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
