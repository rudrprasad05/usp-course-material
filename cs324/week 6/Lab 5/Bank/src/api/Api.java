package api;

import java.rmi.*;

public interface Api extends Remote {
    public int setBalance(int value)  throws RemoteException;
    public int addBalance(int value)  throws RemoteException;
    public int withdrawBalance(int value)  throws RemoteException;
    public int addInterest(double interestPercent)  throws RemoteException;

}