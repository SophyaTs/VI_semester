package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    MessageObject registerForGame(MessageObject input) throws RemoteException;
    MessageObject performMove(MessageObject requestMessage) throws RemoteException;
}