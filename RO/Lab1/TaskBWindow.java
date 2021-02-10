package lab1;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Semaphore;

import javax.swing.*;

public class TaskBWindow extends JFrame {

	public final static int MIN = 0;
	public final static int MAX = 10000;
	public final static int MARK = 1000;
	public final static int FIRST_POS = 1000;
	public final static int SECOND_POS = 9000;
	public final static int STEP = 1;
	
	private JSlider slider;
	private JButton startButton1, startButton2, stopButton1, stopButton2;

	private static Semaphore semaphore = new Semaphore(1);
	
	private static class TThread1 implements Runnable{
		private JSlider slider;

		public TThread1(JSlider slider) {
			super();
			this.slider = slider;
		}
		
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
					int pos = slider.getValue();
					pos = FIRST_POS < pos - STEP ? pos - STEP : FIRST_POS;
					slider.setValue(pos);
					//slider.setValue(FIRST_POS);	
					
					System.out.println("Not Interrupted");
			}			
		}		
	}
	
	private static class TThread2 implements Runnable{
		private JSlider slider;

		public TThread2(JSlider slider) {
			super();
			this.slider = slider;
		}

		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
					int pos = slider.getValue();
					pos = SECOND_POS > pos + STEP ? pos + STEP : SECOND_POS;
					slider.setValue(pos);
					//slider.setValue(SECOND_POS);
			}			
		}		
	}
	
	private Thread t1, t2;

	
	TaskBWindow(){
		super("Lab 1: task b)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(10));
		slider = new JSlider(JSlider.HORIZONTAL, MIN,MAX, (MIN+MAX)/2);
		slider.setMajorTickSpacing((MIN+MAX)/2);
		slider.setMinorTickSpacing(MARK);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		//slider.setEnabled(false);
		box.add(slider);
		box.add(Box.createVerticalStrut(10));
		
		startButton1 = new JButton("START 1");
		startButton1.addMouseListener(new MouseAdapter() {			 
			public void mouseClicked(MouseEvent event) {
				boolean free = semaphore.tryAcquire();
				if(free) {
					t1 = new Thread(new TThread1(slider));
					t1.start();
					stopButton2.setEnabled(false);
				} else
					JOptionPane.showMessageDialog(null, "Other thread is running");
			}			 
			});
		box.add(startButton1);
		box.add(Box.createVerticalStrut(5));
		
		stopButton1 = new JButton("STOP  1");
		stopButton1.addMouseListener(new MouseAdapter() {			 
			public void mouseClicked(MouseEvent event) {
				boolean free = semaphore.tryAcquire();
				if(!free){
					t1.interrupt();
					semaphore.release();
					stopButton2.setEnabled(true);
				}
			}			 
			});
		box.add(stopButton1);
		//stopButton1.setEnabled(false);
		box.add(Box.createVerticalStrut(10));
		
		startButton2 = new JButton("START 2");
		startButton2.addMouseListener(new MouseAdapter() {			 
			public void mouseClicked(MouseEvent event) {
				boolean free = semaphore.tryAcquire();
				if(free) {
					t2 = new Thread(new TThread2(slider));
					t2.start();
					stopButton1.setEnabled(false);
				} else 
					JOptionPane.showMessageDialog(null, "Other thread is running");
			}			 
			});
		box.add(startButton2);
		box.add(Box.createVerticalStrut(5));
		
		stopButton2 = new JButton("STOP  2");
		stopButton2.addMouseListener(new MouseAdapter() {			 
			public void mouseClicked(MouseEvent event) {
				boolean free = semaphore.tryAcquire();
				if(!free) {					
					t2.interrupt();
					semaphore.release();
					stopButton1.setEnabled(true);
				}
			}			 
			});
		box.add(stopButton2);
		//stopButton2.setEnabled(false);
		box.add(Box.createVerticalStrut(10));
		
		setContentPane(box);
		pack();
	}
	
	public static void main(String[] args) {
		JFrame myWindow = new TaskBWindow();		
		myWindow.setVisible(true);
	}

}
