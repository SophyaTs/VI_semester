package com;

import java.util.ArrayList;
import java.util.List;

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

public class Client {
	private Connection connection;	
	private Session session;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private static final String split = "#";
	
	Client(){
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = factory.createConnection();
			
			// Start the connection
	        connection.start();

	        // Create a session which is non transactional
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	        // Create Destination queues
	        Destination queueSend = session.createQueue("fromClient");
	        Destination queueRec = session.createQueue("toClient");

	           
	        // Create a producer
	        producer = session.createProducer(queueSend);
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	        consumer = session.createConsumer(queueRec); 
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		       
	}
	
	public Country countryFindById(Long id) {
		var query = "CountryFindById"+split+id.toString();		
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			return new Country(id,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public City cityFindByName(String name) {
		var query = "CityFindByName"+split+name;
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			String[] fields = response.split(split);
			var id = Long.parseLong(fields[0]);
			var countryid = Long.parseLong(fields[1]);
			var population = Long.parseLong(fields[3]);
			return new City(id,countryid,name,population);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Country countryFindByName(String name) {
		var query = "CountryFindByName"+split+name;
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			var responseid = Long.parseLong(response);
			return new Country(responseid,name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean cityUpdate(City city) {
		var query = "CityUpdate"+split+city.getId().toString()+
				"#"+city.getCountryid().toString()+"#"+city.getName()
				+"#"+city.getPopulation().toString();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return false;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean countryUpdate(Country country) {
		var query = "CountryUpdate"+split+country.getId().toString()+
				"#"+country.getName();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(10000000);			
			if(message == null)
				return false;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cityInsert(City city) {
		var query = "CityInsert"+
				"#"+city.getCountryid().toString()+"#"+city.getName()
				+"#"+city.getPopulation().toString();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return false;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean countryInsert(Country country) {
		var query = "CountryInsert"+
				"#"+country.getName();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return false;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean countryDelete(Country country) {
		var query = "CountryDelete"+split+country.getId().toString();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return false;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cityDelete(City city) {
		var query = "CityDelete"+split+city.getId().toString();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return false;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("true".equals(response))
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Country> countryAll(){
		var query = "CountryAll";
		var list = new ArrayList<Country>();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=2) {
				var id = Long.parseLong(fields[i]);
				var name = fields[i+1];
				list.add(new Country(id,name));
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<City> cityAll(){
		var query = "CityAll";
		var list = new ArrayList<City>();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=4) {
				var id = Long.parseLong(fields[i]);
				var countryid = Long.parseLong(fields[i+1]);
				var name = fields[i+2];
				var population = Long.parseLong(fields[i+3]);
				list.add(new City(id,countryid,name,population));
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<City> cityFindByCountryid(Long idc){
		var query = "CityFindByCountryid"+split+idc.toString();
		var list = new ArrayList<City>();
		String response = "";
		try {
			TextMessage mes = session.createTextMessage(query);
			producer.send(mes);
			Message message = consumer.receive(5000);			
			if(message == null)
				return null;
			
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;	            	       	            
	            response = textMessage.getText();
			}
			if ("".equals(response))
				return list;
			String[] fields = response.split(split);
			for(int i=0;i<fields.length; i+=4) {
				var id = Long.parseLong(fields[i]);
				var countryid = Long.parseLong(fields[i+1]);
				var name = fields[i+2];
				var population = Long.parseLong(fields[i+3]);
				list.add(new City(id,countryid,name,population));
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void cleanMessages() {
		try {
			Message message = consumer.receiveNoWait();
			while(message != null)
				message = consumer.receiveNoWait();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//(new Client()).test("localhost",12345);
	}
}
