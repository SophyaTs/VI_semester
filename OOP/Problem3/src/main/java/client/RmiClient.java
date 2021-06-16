package client;

import common.ServerInterface;
import common.MessageObject;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RmiClient {

    ServerInterface serverInterface;
    int clientId;

    public RmiClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1");
        serverInterface = (ServerInterface) registry.lookup("GameInstance");
    }

    public MessageObject register(String name) throws RemoteException {
        MessageObject result = serverInterface.registerForGame(new MessageObject(name));
        clientId = result.getClientId();
        return result;
    }

    public String performMove(String move) throws RemoteException {
        serverInterface.performMove(new MessageObject("", clientId, "", move));
        MessageObject response = emptyMove();
        return response.getMyMessage();
    }

    public MessageObject emptyMove() throws RemoteException {
        return serverInterface.performMove(new MessageObject("", clientId, "", "-"));
    }

    public MessageObject lose() throws RemoteException {
        return serverInterface.performMove(new MessageObject("", clientId, "", "LOSE"));
    }

    public int getClientId(){
        return clientId;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException{
        RmiClient client = new RmiClient();

        Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
        System.out.print("Enter your name: ");
        String name = sc.nextLine(); //reads string.

        var mo = client.register(name);
        Integer gameUid = mo.getClientId();
        System.out.println("GAME ID: " + gameUid);
        boolean gameOver = false;
        MessageObject response;
        if(gameUid % 10 == 2){
            response = client.emptyMove();
            if(response.getMyMessage().equals("WIN")){
                gameOver = true;
                System.out.println("You won!");
            } else {
                System.out.println("Opponent's move: " + response.getMyMessage());
            }
        } else {
            System.out.println("You go first!");
        }
        while(!gameOver){
            System.out.print("Enter your next move: ");
            String move = sc.nextLine();
            String message = client.performMove(move);
            if(message.equals("WIN")){
                gameOver = true;
                System.out.println("You won!");
            } else {
                System.out.println("Opponent's move: " + message);
            }
        }
    }
}