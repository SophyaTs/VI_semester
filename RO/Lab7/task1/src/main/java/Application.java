import components.DuckAnimation;
import components.JPanelWithBackground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Application extends JFrame {
    private void initializeFrame(JFrame frame, JPanel panel) {
        frame.setContentPane(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(panel.getPreferredSize());
        frame.setTitle("Speedrun man hunt");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializePanel(JPanel panel) throws IOException {
        ImageIcon duckIcon = new ImageIcon(
                new ImageIcon(ImageIO.read(this.getClass().getResource("duck.png")))
                        .getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        for (int i = 0; i < 5; i++) {
            DuckAnimation duck = new DuckAnimation(duckIcon);
            panel.add(duck);
            duck.startAction();
        }
    }

    private Application() throws IOException {
        JFrame frame = new JFrame();
        JPanel panel = new JPanelWithBackground(this.getClass().getResource("back.png"));

        initializePanel(panel);

        initializeFrame(frame, panel);
    }

    public static void main(String... args) throws IOException {
        new Application();
    }
}