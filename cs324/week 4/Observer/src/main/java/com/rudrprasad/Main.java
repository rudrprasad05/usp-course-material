package com.rudrprasad;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {
        Number n = new Number();
        Scanner in = new Scanner(System.in);

        new HexNumber( n );
        new BinNumber( n );
        while (true) {
            System.out.print( "\nEnter a number: " );
            n.setValue( in.nextInt() );
        }
    }
}