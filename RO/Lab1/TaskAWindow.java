package lab1;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class TaskAWindow extends JFrame {
	public final static int MIN = 0;
	public final static int MAX = 10000;
	public final static int MARK = (int) (0.1 * MAX);
	public final static int FIRST_POS = (int) (0.1 * MAX);
	public final static int SECOND_POS = (int) (0.9*MAX);
	public final static int STEP = 10;
	public final static int SLEEP = 200;
	
	private JSlider slider;
	private JRadioButton rButtonNone;
	
	private static class TThread1 implements Runnable{
		private JSlider slider;

		public TThread1(JSlider slider) {
			super();
			this.slider = slider;
		}
		
		public void run() {
			while(!Thread.currentThread().isInterrupted()) {
				synchronized(slider) {
					int pos = slider.getValue();
					pos = FIRST_POS < pos - STEP ? pos - STEP : FIRST_POS;
					slider.setValue(pos);
					//slider.setValue(FIRST_POS);
					
					try {
						Thread.sleep(SLEEP/Thread.currentThread().getPriority());
					} catch (InterruptedException e) {
						return;				
					}
				}								
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
				synchronized(slider) {
					int pos = slider.getValue();
					pos = SECOND_POS > pos + STEP ? pos + STEP : SECOND_POS;
					slider.setValue(pos);
					//slider.setValue(SECOND_POS);	
					
					try {
						Thread.sleep(SLEEP/Thread.currentThread().getPriority());
					} catch (InterruptedException e) {
						return;
					}				
				}
			}			
		}		
	}
	
	private Thread t1, t2;

	
	TaskAWindow(){
		super("Lab 1: task a)");
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
		
		JRadioButton rButton1 = new JRadioButton("Prioritize TThread1");
		rButton1.addItemListener(new ItemListener() {			 
			public void itemStateChanged(ItemEvent event){
				if (event.getStateChange() == ItemEvent.SELECTED && t1.isAlive() && t2.isAlive()) {
					t1.setPriority(Thread.MAX_PRIORITY);
					t2.setPriority(Thread.MIN_PRIORITY);
					System.out.printf("T1 %d\n",t1.getPriority());
					System.out.printf("T2 %d\n\n",t2.getPriority());
				}
			}			 
			});		
		JRadioButton rButton2 = new JRadioButton("Prioritize TThread2");
		rButton2.addItemListener(new ItemListener() {			 
			public void itemStateChanged(ItemEvent event){
				if(event.getStateChange() == ItemEvent.SELECTED && t1.isAlive() && t2.isAlive())
				{
					t2.setPriority(Thread.MAX_PRIORITY);
					t1.setPriority(Thread.MIN_PRIORITY);
					System.out.printf("T1 %d\n",t1.getPriority());
					System.out.printf("T2 %d\n\n",t2.getPriority());
				}
			}			 
			});
		rButtonNone = new JRadioButton("Prioritize none");
		rButtonNone.setSelected(true);
		rButtonNone.addItemListener(new ItemListener() {			 
			public void itemStateChanged(ItemEvent event){
				if(event.getStateChange() == ItemEvent.SELECTED && t1.isAlive() && t2.isAlive())
				{
					t1.setPriority(Thread.NORM_PRIORITY);
					t2.setPriority(Thread.NORM_PRIORITY);
					System.out.printf("T1 %d\n",t1.getPriority());
					System.out.printf("T2 %d\n\n",t2.getPriority());
				}
			}			 
			});
		ButtonGroup bg = new ButtonGroup();
		bg.add(rButton1);
		bg.add(rButton2);
		bg.add(rButtonNone);
		box.add(rButton1);
		box.add(rButton2);
		box.add(rButtonNone);		
		box.add(Box.createVerticalStrut(10));
		
		JButton startButton = new JButton("START");
		startButton.addMouseListener(new MouseAdapter() {			 
			public void mouseClicked(MouseEvent event) {
				if((t1 == null && t2 == null) || !(t1.isAlive() && t2.isAlive()) ) {
					t1 = new Thread(new TThread1(slider));
					t2 = new Thread(new TThread2(slider));
					t1.setDaemon(true);
					t2.setDaemon(true);
					t1.start();
					t2.start();
					rButtonNone.setSelected(true);
				}
			}			 
			});
		box.add(startButton);
		box.add(Box.createVerticalStrut(5));
		
		JButton stopButton = new JButton("STOP");
		stopButton.addMouseListener(new MouseAdapter() {			 
			public void mouseClicked(MouseEvent event) {
				if(t1.isAlive() && t2.isAlive()) {
					t1.interrupt();
					t2.interrupt();
				}
			}			 
			});
		box.add(stopButton);
		box.add(Box.createVerticalStrut(10));
		
		setContentPane(box);
		pack();
	}
	
	public static void main(String[] args) {
		JFrame myWindow = new TaskAWindow();		
		myWindow.setVisible(true);
	}

}
