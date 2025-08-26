package bank;

import java.rmi.registry.*;
import api.*;

public class Bank {

    private void startServer(){
        try {
            // create on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // create a new service named myMessage
            registry.rebind(Api.class.getSimpleName(), new ApiImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }      
        System.out.println("system is ready");
    }
    
    public static void main(String[] args) {
        Bank main = new Bank();
        main.startServer();
    }
}