package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cli.Actions;
import com.entity.Containing;
import com.entity.Order;
import com.entity.Product;

public interface RmiServer extends Remote, Actions{
	public void createOrder(Order e) throws RemoteException;
	public Order readOrder(long id) throws RemoteException;
	public void updateOrder(Order e) throws RemoteException;
	public void deleteOrder(long id) throws RemoteException;
	
	public void createProduct(Product e) throws RemoteException;
	public Product readProduct(long id) throws RemoteException;
	public void updateProduct(Product e) throws RemoteException;
	public void deleteProduct(long id) throws RemoteException;
	
	public void createContaining(Containing e) throws RemoteException;
	public Containing readContaining(long orderid, long productid) throws RemoteException;
	public void updateContaining(Containing e) throws RemoteException;
	public void deleteContaining(long orderid, long productid) throws RemoteException;
	
	public String f1(long id) throws RemoteException;
	public List<Long> f2(long id) throws RemoteException;
	public List<Long>  f3(long id) throws RemoteException;
	public void f4() throws RemoteException;
	public void f5(long id, long q) throws RemoteException;
}
