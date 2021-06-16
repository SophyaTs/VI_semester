package gui;

import javax.swing.*;
import java.awt.*;

public class StartGameScreen extends JComponent {
    final int width;
    final int height;
    @Override
    public void paint(Graphics G){
        G.drawRect(0,0,100,50);
    }

    public StartGameScreen(int width, int height){
        this.width = width;
        this.height = height;
    }
}
