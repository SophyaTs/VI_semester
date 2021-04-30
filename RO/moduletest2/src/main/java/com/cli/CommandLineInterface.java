package com.cli;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Scanner;

import com.entity.Containing;
import com.entity.Order;
import com.entity.Product;

public class CommandLineInterface {
	Scanner in = new Scanner(System.in);
	Actions act;

	public CommandLineInterface(Actions act) {
		super();
		this.act = act;
	}

	public void interact_blin_gejidgjididi(String[] args) throws RemoteException{
		int choice = 100000;
		long aux;
		String str;
		while(choice > 0) {
			System.out.println("Choose option:\n"
					+ "1 - create Order\n"
					+ "2 - read Order\n"
					+ "3 - update Order\n"
					+ "4 - delete Order\n"
					+ "5 - create Product\n"
					+ "6 - read Product\n"
					+ "7 - update Product\n"
					+ "8 - delete Product\n"
					+ "9 - create Containing\n"
					+ "10 - read Containing\n"
					+ "11 - update Containing\n"
					+ "12 - delete Containing\n"
					+ "13 - get all information about Order\n"
					+ "14 - get all Orders with Product\n"
					+ "15 - get today's Orders without Product\n"
					+ "16 - place new Order with the today's products\n"
					+ "17 - delete Orders with studid conditions\n"
					+ "18 - filter Orders by cost and products quantity\n"
					+ "anything else - FINALLY EXIT"
					);
			choice = in.nextInt();
			switch (choice) {
			case 1:
				var millis = System.currentTimeMillis();
				var order = new Order();
				order.setDate(new Date(millis));
				act.createOrder(order);
				System.out.println("Ok, creating new order, its date is today!");
				break;
			case 2:
				System.out.println("Enter id:");
				aux = in.nextLong();
				order = act.readOrder(aux);
				System.out.println(order);
				break;
			case 3:
				millis = System.currentTimeMillis();
				System.out.println("Enter id:");
				aux = in.nextLong();
				order = new Order();
				order.setId(aux);
				order.setDate(new Date(millis));
				System.out.println("Ok, updating this order, its date is today!");
				break;
			case 4:
				System.out.println("Enter id:");
				aux = in.nextLong();
				act.deleteOrder(aux);
				System.out.println("Deleting...");
				break;
			case 5:			
				var product = new Product();
				System.out.println("Enter name:");
				str = in.next();
				product.setName(str);
				System.out.println("Enter desription:");
				str = in.next();
				product.setDescription(str);				
				System.out.println("Enter price:");
				aux = in.nextLong();
				product.setPrice(aux);
				act.updateProduct(product);
				act.createProduct(product);
				System.out.println("Creating new product...");
				break;
			case 6:
				System.out.println("Enter id:");
				aux = in.nextLong();
				product = act.readProduct(aux);
				System.out.println(product);
				break;
			case 7:
				System.out.println("Enter id:");
				product = new Product();
				aux = in.nextLong();
				product.setId(aux);
				System.out.println("Enter name:");
				str = in.next();
				product.setName(str);
				System.out.println("Enter desription:");
				str = in.next();
				product.setDescription(str);				
				System.out.println("Enter price:");
				aux = in.nextLong();
				product.setPrice(aux);
				act.updateProduct(product);
				System.out.println("Updating product...");
				break;
			case 8:
				System.out.println("Enter id:");
				aux = in.nextLong();
				act.deleteProduct(aux);
				System.out.println("Deleting...");
				break;
			case 9:
				System.out.println("Enter orderid, productid, quantity:");
				var cont = new Containing();
				aux = in.nextLong();
				cont.setOrderid(aux);
				aux = in.nextLong();
				cont.setProductid(aux);
				aux = in.nextLong();
				cont.setQuantity(aux);
				act.createContaining(cont);
				System.out.println("Adding product to order...");
				break;
			case 10:
				System.out.println("Enter orderid, productid:");
				aux = in.nextLong();
				var aux2 = in.nextLong();
				cont = act.readContaining(aux,aux2);
				System.out.println(cont);
				break;
			case 11:
				System.out.println("Enter orderid, productid, quantity:");
				cont = new Containing();
				aux = in.nextLong();
				cont.setOrderid(aux);
				aux = in.nextLong();
				cont.setProductid(aux);
				aux = in.nextLong();
				cont.setQuantity(aux);
				act.updateContaining(cont);
				System.out.println("Updating order details...");
				break;
			case 12:
				System.out.println("Enter orderid, productid:");
				aux = in.nextLong();
				aux2 = in.nextLong();
				act.deleteContaining(aux,aux2);
				System.out.println("Deleting...");
				break;
			case 13:
				System.out.println("Enter positive number, I don't remember what for:");
				aux = in.nextLong();
				System.out.println(act.f1(aux));
				break;
			case 14:
				System.out.println("Enter positive number, I don't remember what for:");
				aux = in.nextLong();
				var list = act.f2(aux);
				System.out.println(list);
				break;
			case 15:
				System.out.println("Enter positive number, I don't remember what for:");
				aux = in.nextLong();
				list = act.f3(aux);
				System.out.println(list);
				break;
			case 16:
				System.out.println("Ok");
				act.f4();
				break;
			case 17:
				System.out.println("Enter 2 positive numbers, I don't remember what for:");
				aux = in.nextLong();
				aux2 = in.nextLong();
				act.f5(aux, aux2);
				System.out.println("Ok");
				break;
			case 18:
				System.out.println("Enter 2 positive numbers, I don't remember what for:");
				aux = in.nextLong();
				aux2 = in.nextLong();
				list = act.f6(aux,aux2);
				System.out.println(list);
				break;
			default:
				return;
			}
		}
	}

}
