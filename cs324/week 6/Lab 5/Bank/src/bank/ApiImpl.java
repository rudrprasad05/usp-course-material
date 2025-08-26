package bank;

import java.rmi.*;
import java.rmi.server.*;
import api.*;

public class ApiImpl extends UnicastRemoteObject implements Api {
    private static final long serialVersionUID = 1L;
    private Data account = new Data(0);

    public ApiImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized int setBalance(int value) throws RemoteException {
        account.setValue(value);
        System.out.println("new balance: " + account.getValue());
        return account.getValue();
    }

    @Override
    public int addBalance(int value) throws RemoteException {
        account.setValue(account.getValue() + value);
        System.out.println("new balance: " + account.getValue());
        return account.getValue();
    }

    @Override
    public int withdrawBalance(int value) throws RemoteException {
        account.setValue(account.getValue() - value);
        System.out.println("new balance: " + account.getValue());
        return account.getValue();
    }

    @Override
    public int addInterest(double interestPercent) throws RemoteException {
        int current = account.getValue();
        int afterInterest = (int) (current * (1+interestPercent));
        account.setValue(afterInterest);
        System.out.println("new balance: " + account.getValue());
        return account.getValue();
    }

}