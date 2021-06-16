package server;

import common.ServerInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RmiServer {
    public static void main(String[] args){


        ServerInterface gameInstance = new ServerObject();
        try{
            ServerInterface gameInterface = (ServerInterface) UnicastRemoteObject.exportObject(gameInstance, 0);
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.bind("GameInstance", gameInterface);
            System.out.println("Server is up and running!");
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println("Couldn't start RMI server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
