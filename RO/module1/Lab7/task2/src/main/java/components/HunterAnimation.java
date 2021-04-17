package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HunterAnimation extends JLabel implements KeyListener {
    private int deltaX;
    private int directionX = 1;
    private int delay;
    private int currX;
    private int parentHeight;
    private int parentWidth;
    private ImageIcon spellIcon;

    private volatile List<DuckAnimation> ducks = new ArrayList<>();

    public HunterAnimation(ImageIcon resource, ImageIcon spellResource, List<DuckAnimation> ducks) {
        super(resource);
        this.spellIcon = spellResource;
        this.ducks.addAll(ducks);
        this.deltaX = getRandomNumber(0, 2);
        this.delay = getRandomNumber(10, 40);
        this.addKeyListener(this);
    }

    private int getRandomNumber(int min, int max) {
        int num = 0;
        while (num == 0) {
            num = (int) ((Math.random() * (max - min)) + max);
        }
        return num;
    }

    private void performMovement() {
        int nextX = getLocation().x + (deltaX * directionX);
        if (nextX < 0) {
            nextX = 0;
            directionX *= -1;
        }
        if (nextX + getSize().width > parentWidth) {
            nextX = parentWidth - getSize().width;
            directionX *= -1;
        }

        setLocation(nextX, parentHeight - 3 * this.getHeight() / 4);
        this.currX = nextX;
    }

    public void startAction() {
        Container parent = getParent();
        parentHeight = parent.getHeight();
        parentWidth = parent.getWidth();
        setLocation(0, parentHeight - 3 * this.getHeight() / 4);

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                performMovement();
            }
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(repeatedTask, 0, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        setLocation(currX, parentHeight - 3 * this.getHeight() / 4);
        super.paintComponent(g);
    }

    private synchronized void shoot() {
        Iterator ducksIterator = ducks.iterator();
        while (ducksIterator.hasNext()) {
            DuckAnimation duck = (DuckAnimation) ducksIterator.next();
            if (Math.abs(duck.getCurrX() - currX) < duck.getWidth() / 2) {
                duck.getShot(spellIcon);
                ducksIterator.remove();
                return;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shoot();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
