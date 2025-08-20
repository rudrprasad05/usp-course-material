package com.rudrprasad;

public class BinNumber implements MyObserver {
    private Number n;
    private BinNumber( ) {
        ;
    }
    public BinNumber( Number in ) {
        this.n = in;
        n.addObserver( this );
    }
    public void update() {
        System.out.print( " " + Integer.toBinaryString(
                n.getValue() ) );
    }
}