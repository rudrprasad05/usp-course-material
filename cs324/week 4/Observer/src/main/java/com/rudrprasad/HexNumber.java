package com.rudrprasad;

public class HexNumber implements MyObserver {
    private Number n;
    public HexNumber( Number in ) {
        this.n = in;
        n.addObserver( this );
    }
    public void update() {
        System.out.print( " " + Integer.toHexString(
                n.getValue() ) );
    }
}
