package com.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dao.ContainingDAO;
import com.dao.OrderDAO;
import com.dao.ProductDAO;
import com.entity.*;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

public class SocketServer {
	public static final int THREADS = 5;
	
	private ServerSocket server = null;
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public void start(int port) throws IOException {
		server = new ServerSocket(port);
		sock = server.accept();
		in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
		out = new PrintWriter(sock.getOutputStream(), true);
		ExecutorService agent = Executors.newFixedThreadPool(THREADS);
		while (true) {
			String query = in.readLine();
			agent.execute(new processQuery(out,query));
		}
	}
	
	@AllArgsConstructor
	private static class processQuery implements Runnable {
		private PrintWriter out;
		private String query;

		@Override
		public void run() {
			if (query == null) 
				return;
			String[] fields = query.split("#");
			if (fields.length == 0)
				return;
			
			var action = fields[0];
			
			String response = "";
			switch (action) {
			case "cO":
				var order = new Gson().fromJson(fields[1], Order.class);
				OrderDAO.insert(order);
				break;
			case "rO":
				long id = Long.parseLong(fields[1]);
				order = OrderDAO.findById(id);
				response = new Gson().toJson(order);
				break;
			case "uO":
				order = new Gson().fromJson(fields[1], Order.class);
				OrderDAO.update(order);
				break;
			case "dO":
				id = Long.parseLong(fields[1]);
				OrderDAO.delete(id);
				break;
			
			case "cP":
				var product = new Gson().fromJson(fields[1], Product.class);
				ProductDAO.insert(product);
				break;
			case "rP":
				id = Long.parseLong(fields[1]);
				product = ProductDAO.findById(id);
				response = new Gson().toJson(product);
				break;
			case "uP":
				product = new Gson().fromJson(fields[1], Product.class);
				ProductDAO.update(product);
				break;
			case "dP":
				id = Long.parseLong(fields[1]);
				ProductDAO.delete(id);
				break;
			
			case "cC":
				var cont = new Gson().fromJson(fields[1], Containing.class);
				ContainingDAO.insert(cont);
				break;
			case "rC":
				id = Long.parseLong(fields[1]);
				var aux = Long.parseLong(fields[2]);
				cont = ContainingDAO.findById(id,aux);
				response = new Gson().toJson(cont);
				break;
			case "uC":
				cont = new Gson().fromJson(fields[1], Containing.class);
				ContainingDAO.update(cont);
				break;
			case "dC":
				id = Long.parseLong(fields[1]);
				aux = Long.parseLong(fields[2]);
				ContainingDAO.delete(id,aux);
				break;
			
			case "f1":
				id = Long.parseLong(fields[1]);
				response = OrderDAO.getPrettyInfo(id);
				break;
			case "f2":
				id = Long.parseLong(fields[1]);
				var list = OrderDAO.findByProductid(id);
				response = new Gson().toJson(list);
				break;
			case "f3":
				id = Long.parseLong(fields[1]);
				list = OrderDAO.findByNotProductidAndToday(id);
				response = new Gson().toJson(list);
				break;
			case "f4":
				OrderDAO.placeTodayOrder();
				break;
			case "f5":
				id = Long.parseLong(fields[1]);
				var q = Long.parseLong(fields[2]);
				OrderDAO.deleteSpecial(id, q);
				break;
			case "f6":
				var p = Long.parseLong(fields[1]);
				q = Long.parseLong(fields[2]);
				list = OrderDAO.filterByCostAndQuantity(p, q);
				response = new Gson().toJson(list);
				break;
			}
			out.println(response);
		}
		
	}
	
	public static void main(String[] args) {
		try {
			SocketServer srv = new SocketServer();
			srv.start(12345);
		} catch(IOException e) {
			System.out.println("Error");
		}
	}
}
