package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DuckAnimation extends JLabel implements MouseListener {
    private int deltaX;
    private int deltaY;
    private int directionX;
    private int directionY;
    private int delay;
    private volatile int currX;
    private volatile int currY;
    private ScheduledExecutorService executor;


    public DuckAnimation(ImageIcon resource) {
        super(resource);
        this.deltaX = getRandomNumber(0, 4);
        this.deltaY = getRandomNumber(0, 4);
        this.directionX = new Random().nextBoolean() ? 1 : -1;
        this.directionY = new Random().nextBoolean() ? 1 : -1;
        this.delay = getRandomNumber(10, 60);
        this.addMouseListener(this);
    }


    private int getRandomPositionOnMap(int max) {
        return (int) (Math.random() * (max));
    }

    private int getRandomNumber(int min, int max) {
        int num = 0;
        while (num == 0) {
            num = (int) ((Math.random() * (max - min)) + max);
        }
        return num;
    }

    private void performMovement() {
        Container parent = getParent();

        int nextX = getLocation().x + (deltaX * directionX);
        if (nextX < 0) {
            nextX = 0;
            directionX *= -1;
        }
        if (nextX + getSize().width > parent.getSize().width) {
            nextX = parent.getSize().width - getSize().width;
            directionX *= -1;
        }

        int nextY = getLocation().y + (deltaY * directionY);
        if (nextY < 0) {
            nextY = 0;
            directionY *= -1;
        }
        if (nextY + getSize().height > parent.getSize().height / 3) {
            nextY = parent.getSize().height / 3 - getSize().height;
            directionY *= -1;
        }

        setLocation(nextX, nextY);
        this.currX = nextX;
        this.currY = nextY;
    }

    public void startAction() {
        Container parent = getParent();

        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight() / 3;
        setLocation(getRandomPositionOnMap(parentWidth), getRandomPositionOnMap(parentHeight));

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                performMovement();
            }
        };

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(repeatedTask, 0, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        setLocation(currX, currY);
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Container parent = this.getParent();
        parent.remove(this);
        parent.repaint();
        executor.shutdownNow();
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
