/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.rmi.registry.*;
import java.util.Scanner;

import api.*;

public class Customer {
    private static final String HOST = "localhost";
    private static final int PORT = 1099;
    private static Registry registry;
    private static int input;
    
    public static void main(String[] args) throws Exception {
        registry = LocateRegistry.getRegistry(HOST, PORT);
        Api remoteApi = (Api) registry.lookup(Api.class.getSimpleName());

        System.out.println("Select a menu option [1-3]: ");
        System.out.println(
                """
                1. View funds
                2. withdraw funds
                3. deposit funds
                """);

        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();

        switch (input) {
            case 1:
                viewFunds(remoteApi);
                break;
            case 2:
                withdrawFunds(remoteApi);
                break;
            case 3:
                depositFunds(remoteApi);
                break;
            default:
                System.out.println("Invalid option");
        }
        

        
    }

    private static void viewFunds(Api api) throws Exception{
        System.out.println("New balance = " + api.addBalance(0));
    }

    private static void withdrawFunds(Api api) throws Exception{
        System.out.println("Enter withdrawal amount: ");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();

        System.out.println("New balance = " + api.withdrawBalance(input));
    }

    private static void depositFunds(Api api) throws Exception{
        System.out.println("Enter deposit amount: ");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();

        System.out.println("New balance = " + api.addBalance(input));
    }
}