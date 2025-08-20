package org.example;

/*
 * @author
 * Rudr Prasad - S11219309
 * Ahad Ali - S11221529
 * Rishal Prasad - S11221067
 *
 */

public class HelperFunctions {

    public static Double parseDouble(String str) {
        if (str.isEmpty()) {
            return null;
        }
        try{
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e){
            System.out.println(e);

        }
        return 0.0;
    }

    public static Integer parseInt(String str) {
        if (str.isEmpty()) {
            return null;
        }
        try{
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            System.out.println(e);

        }
        return 0;
    }
}
