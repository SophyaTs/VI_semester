package com.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import com.cli.Actions;
import com.cli.CommandLineInterface;
import com.entity.Containing;
import com.entity.Order;
import com.entity.Product;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/* Normally all these methods should not throw RemoteException,
 * but for the sake of less copy-pasting cli I've made one interface Actions
 * for both socket and rmi tasks, and RemoteException is needed for rmi to work*/
public class SocketClient implements Actions{
	private Socket sock = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public SocketClient(String ip, int port) throws IOException{
		// establish connection
		sock = new Socket(ip,port);
		// get input/output streams
		in = new BufferedReader(
		new InputStreamReader(sock.getInputStream( )));
		out = new PrintWriter(sock.getOutputStream(), true);
	}
	
	@Override
	public void createOrder(Order e) throws RemoteException {
		var query = "cO#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public Order readOrder(long id) throws RemoteException {
		var query = "rO#"+id;
		out.println(query);
		try {
			return new Gson().fromJson(in.readLine(), Order.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateOrder(Order e) throws RemoteException {
		var query = "uO#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public void deleteOrder(long id) throws RemoteException {
		var query = "dO#"+id; 
		out.println(query);
	}

	@Override
	public void createProduct(Product e) throws RemoteException {
		var query = "cP#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public Product readProduct(long id) throws RemoteException {
		var query = "rP#"+id; 
		out.println(query);
		try {
			return new Gson().fromJson(in.readLine(), Product.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProduct(Product e) throws RemoteException {
		var query = "uP#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public void deleteProduct(long id) throws RemoteException {
		var query = "dP#"+id; 
		out.println(query);
	}

	@Override
	public void createContaining(Containing e) throws RemoteException {
		var query = "cC#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public Containing readContaining(long orderid, long productid) throws RemoteException {
		var query = "rC#"+orderid+"#"+productid; 
		out.println(query);
		try {
			return new Gson().fromJson(in.readLine(), Containing.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateContaining(Containing e) throws RemoteException {
		var query = "uC#"+(new Gson().toJson(e));
		out.println(query);
	}

	@Override
	public void deleteContaining(long orderid, long productid) throws RemoteException {
		var query = "dC#"+orderid+"#"+productid; 
		out.println(query);
	}

	@Override
	public String f1(long id) throws RemoteException {
		var query = "f1#"+id; 
		out.println(query);
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Long> f2(long id) throws RemoteException {
		var query = "f2#"+id; 
		out.println(query);
		
		Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
		try {
			return new Gson().fromJson(in.readLine(), ListType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Long> f3(long id) throws RemoteException {
		var query = "f3#"+id; 
		out.println(query);
		
		Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
		try {
			return new Gson().fromJson(in.readLine(), ListType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void f4() throws RemoteException {
		var query = "f4"; 
		out.println(query);		
	}

	@Override
	public void f5(long id, long q) throws RemoteException {
		var query = "f5#"+id+"#"+q; 
		out.println(query);		
	}
	
	@Override
	public List<Long> f6(long c, long q) throws RemoteException {
		var query = "f6#"+c+"#"+q; 
		out.println(query);
		
		Type ListType = new TypeToken<ArrayList<Long>>(){}.getType();
		try {
			return new Gson().fromJson(in.readLine(), ListType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		var client = new SocketClient("localhost",12345);
		var cli = new CommandLineInterface(client);
		cli.interact_blin_gejidgjididi(args);
	}
	
}
