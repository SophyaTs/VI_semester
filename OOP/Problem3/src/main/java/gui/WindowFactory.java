package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowFactory {
    public static Window GetWindow(GUI gui, GUI.GuiState state){
        return switch (state) {
            case StartScreen -> createStartWindow(gui);
            case Preparation -> createConstructorWindow(gui);
            case Game -> createGameWindow(gui);
            case WIN -> createRestartWindow(gui, true);
            case LOSE -> createRestartWindow(gui, false);
            default -> new Window(new JFrame(), "Alter", gui);
        };
    }
    private static JFrame defaultJFrame(String title){
        JFrame f = new JFrame(title);
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        f.setBounds(dimension.width/2 - Window.width/2, dimension.height/2 - Window.height/2, Window.width,Window.height);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        return f;
    }

    private static JFrame GameFrame(String title){
        JFrame f = defaultJFrame(title);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        f.setBounds(dimension.width/2 - Window.width/2, dimension.height/2 - Window.height/2, Window.width * 3 / 2,Window.height);
        return f;
    }

    private static Window createStartWindow(GUI gui){
        JFrame startGameFrame = defaultJFrame("Start Screen");
        var window = new Window(startGameFrame,  "Start Screen", gui);

        JButton startButton = new JButton("START");
        startButton.setActionCommand("START");
        window.addButton(startButton,"START", 250,225,300, 50);

        JLabel label = new JLabel("",JLabel.CENTER );
        window.addLabel(label,"HEAD", 350,175,200, 50);

        return window;
    }


    private static Window createConstructorWindow(GUI gui){
        JFrame loadingFrame = defaultJFrame("Game preparations");
        var window = new ConstructionWindow(loadingFrame,  "Place your ships", gui);

        JLabel label = new JLabel("Place your ships!",JLabel.CENTER );
        window.addLabel(label,"PLACE", 325,0,150, 50);

        JButton startButton = new JButton("READY");
        startButton.setActionCommand("READY");
        window.addButton(startButton,"READY", 685,500,100, 50);

        JButton rotateButton = new JButton("ROTATE");
        rotateButton.setActionCommand("ROTATE");
        window.addButton(rotateButton,"ROTATE", 685,430,100, 50);

        window.addField(100,100);
        window.ships = GuiShip.getStartingShips();

        for (int i = 0; i < 10; i++) {
            System.out.println(window.ships[i].size);
            for(int t = 0; t < window.ships[i].size; t++){
                int shipBlock = 20;
                JButton shipButton = new JButton();
                shipButton.setActionCommand("ship" + i);
                window.addButton(shipButton, "ship" + i + "" + t, window.ships[i].x + t * shipBlock, window.ships[i].y, shipBlock, shipBlock);
            }
        }

        return window;
    }

    private static Window createGameWindow(GUI gui){
        JFrame loadingFrame = GameFrame("Battle sea");
        var window = new GameWindow(loadingFrame, "Battle sea", gui);

        JLabel label = new JLabel("HEAD",JLabel.CENTER );
        if(window.yourMove){
            label.setText("Your move");
        }
        window.addLabel(label,"HEAD", 325,0,150, 50);



        JButton exitButton = new JButton("EXIT");
        exitButton.setActionCommand("EXIT");
        window.addButton(exitButton,"EXIT", 685,500,100, 50);

        window.addMyField(100,100);
        window.addField(700,100);

        return window;
    }

    private static Window createRestartWindow(GUI gui, boolean win){
        JFrame startGameFrame;
        Window window;
        if(win){
            startGameFrame = defaultJFrame("You win!");
            window = new Window(startGameFrame,  "You win!", gui);
        } else {
            startGameFrame = defaultJFrame("You lost!");
            window = new Window(startGameFrame,  "You lost!", gui);
        }

        JButton startButton = new JButton("RESTART");
        startButton.setActionCommand("RESTART");
        window.addButton(startButton,"RESTART", 250,225,300, 50);

        JLabel label;
        if(win)
            label = new JLabel("You win!",JLabel.CENTER );
        else
            label = new JLabel("You lost!",JLabel.CENTER );
        window.addLabel(label,"HEAD", 350,175,200, 50);

        return window;
    }
}