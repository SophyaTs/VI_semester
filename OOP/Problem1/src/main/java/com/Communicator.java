package com;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Communicator {
	public static final String XtoO = "XtoO";
	public static final String OtoX = "OtoX";
	
	private Connection connection;	
	private Session session;
	private Destination  queueSend;
	private Destination  queueRec;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private String buffer;
	
	public Communicator(int p){
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        //Create connection.
        try {
			connection = factory.createConnection();
			// Start the connection
	        connection.start();

	        // Create a session which is non transactional
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        // Create Destination queues
	        if (p == GameManager.X) {
	        	queueSend = session.createQueue(XtoO);
	        	queueRec = session.createQueue(OtoX);
	        } else {
	        	queueSend = session.createQueue(OtoX);
	        	queueRec = session.createQueue(XtoO);
	        }
	           
	        // Create a producer
	        producer = session.createProducer(queueSend);
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	        consumer = session.createConsumer(queueRec);        
		
        } catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}

	public void sendMove(int i, int j) {
		try {
			TextMessage message = session.createTextMessage("Move#"+i+"#"+j);
			producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendWin(int i, int j) {
		try {
			TextMessage message = session.createTextMessage("Win#"+i+"#"+j);
			producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendTie(int i, int j) {
		try {
			TextMessage message = session.createTextMessage("Tie#"+i+"#"+j);
			producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int[] recieveMessage() {		
		try {
			Message message = consumer.receive(500);
			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            String buffer = textMessage.getText();
	            var fields = buffer.split("#");
	            
	            int[] move = {0,-1,-1};
	            if("Win".equals(fields[0]))
	            	move[0]= -1;
	            if("Tie".equals(fields[0]))
	            	move[0]= -2;
	            
	            move[1] = Integer.parseInt(fields[1]);
	            move[2] = Integer.parseInt(fields[2]);	
	            return move;
	        }
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public void close() {
		try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
