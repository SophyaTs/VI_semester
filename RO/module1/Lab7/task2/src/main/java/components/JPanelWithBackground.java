package components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class JPanelWithBackground extends JPanel {
    private Image backgroundImage;

    public JPanelWithBackground(URL backResource) throws IOException {
        backgroundImage = ImageIO.read(backResource);
        this.setPreferredSize(new Dimension(
                backgroundImage.getWidth(null), backgroundImage.getHeight(null)));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
