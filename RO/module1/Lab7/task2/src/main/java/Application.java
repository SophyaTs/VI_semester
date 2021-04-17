import components.DuckAnimation;
import components.HunterAnimation;
import components.JPanelWithBackground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends JFrame {
    private List<DuckAnimation> ducks = new ArrayList<>();
    private HunterAnimation hunter;

    private void initializeFrame(JFrame frame, JPanel panel) {
        frame.setContentPane(panel);
        setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(panel.getPreferredSize());
        frame.setTitle("Speedrun man hunt");
        frame.setVisible(true);
    }

    private void initializePanel(JPanel panel) throws IOException {
        setLayout(null);
        ImageIcon duckIcon = new ImageIcon(
                new ImageIcon(ImageIO.read(this.getClass().getResource("duck.png")))
                        .getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        for (int i = 0; i < 5; i++) {
            DuckAnimation duck = new DuckAnimation(duckIcon);
            ducks.add(duck);
            panel.add(duck);
        }

        ImageIcon hunterIcon = new ImageIcon(
                new ImageIcon(ImageIO.read(this.getClass().getResource("hunter.png")))
                        .getImage().getScaledInstance(173, 250, Image.SCALE_DEFAULT));
        ImageIcon spellIcon = new ImageIcon(
                new ImageIcon(ImageIO.read(this.getClass().getResource("spell.png")))
                        .getImage().getScaledInstance(100, 60, Image.SCALE_DEFAULT));
        hunter = new HunterAnimation(hunterIcon, spellIcon, ducks);
        panel.add(hunter);
    }

    private Application() throws IOException {
        JFrame frame = new JFrame();
        JPanel panel = new JPanelWithBackground(this.getClass().getResource("back.png"));

        initializePanel(panel);
        initializeFrame(frame, panel);
        for (DuckAnimation duck : ducks) {
            duck.startAction();
        }
        hunter.startAction();
        hunter.setFocusable(true);
        hunter.requestFocusInWindow();
    }

    public static void main(String... args) throws IOException {
        new Application();
    }
}