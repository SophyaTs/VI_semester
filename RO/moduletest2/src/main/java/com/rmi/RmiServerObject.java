package com.rmi;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.dao.ContainingDAO;
import com.dao.OrderDAO;
import com.dao.ProductDAO;
import com.entity.Containing;
import com.entity.Order;
import com.entity.Product;

public class RmiServerObject extends UnicastRemoteObject implements RmiServer{

	protected RmiServerObject() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createOrder(Order e) throws RemoteException {
		OrderDAO.insert(e);
	}

	@Override
	public Order readOrder(long id) throws RemoteException {
		return OrderDAO.findById(id);
	}

	@Override
	public void updateOrder(Order e) throws RemoteException {
		OrderDAO.update(e);
	}

	@Override
	public void deleteOrder(long id) throws RemoteException {
		OrderDAO.delete(id);
	}

	@Override
	public void createProduct(Product e) throws RemoteException {
		ProductDAO.insert(e);
	}

	@Override
	public Product readProduct(long id) throws RemoteException {
		return ProductDAO.findById(id);
	}

	@Override
	public void updateProduct(Product e) throws RemoteException {
		ProductDAO.update(e);
	}

	@Override
	public void deleteProduct(long id) throws RemoteException {
		ProductDAO.delete(id);
	}

	@Override
	public void createContaining(Containing e) throws RemoteException {
		ContainingDAO.insert(e);
	}

	@Override
	public Containing readContaining(long orderid, long productid) throws RemoteException {
		return ContainingDAO.findById(orderid, productid);
	}

	@Override
	public void updateContaining(Containing e) throws RemoteException {
		ContainingDAO.update(e);
	}

	@Override
	public void deleteContaining(long orderid, long productid) throws RemoteException {
		ContainingDAO.delete(orderid, productid);
	}

	@Override
	public String f1(long id) throws RemoteException {
		return OrderDAO.getPrettyInfo(id);
	}

	@Override
	public List<Long> f2(long id) throws RemoteException {
		return OrderDAO.findByProductid(id);
	}

	@Override
	public List<Long> f3(long id) throws RemoteException {
		return OrderDAO.findByNotProductidAndToday(id);
	}

	@Override
	public void f4() throws RemoteException {
		OrderDAO.placeTodayOrder();
	}

	@Override
	public void f5(long id, long q) throws RemoteException {
		OrderDAO.deleteSpecial(id, q);
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		var bck = new RmiServerObject();
		Registry registry = LocateRegistry.createRegistry(123);
		registry.rebind("Module2", bck);
		System.out.println("Server started!");
	}

	@Override
	public List<Long> f6(long c, long q) throws RemoteException {
		return OrderDAO.filterByCostAndQuantity(c, q);
	}
	
}
