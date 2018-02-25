package lab1.test;

import lab1.LongNumber;
import lab1.SubtractionException;

import java.math.BigInteger;

public class Test {

    public static void main(String[] args) {

        LongNumber first, second;

        //Add
        first = new LongNumber(20);
        second = new LongNumber(20);
        first.setRandomValues();
        second.setRandomValues();

        System.out.println(first.hex());
        System.out.println(second.hex());

        System.out.println(LongNumber.LongAdd(first, second).hex());

        BigInteger test1 = new BigInteger(first.hex(), 16);
        BigInteger test2 = new BigInteger(second.hex(), 16);
        System.out.println(test1.add(test2).toString(16).toUpperCase());
        System.out.println("--------------------");

        //Sub
        first.setRandomValues();
        second.setRandomValues();

        System.out.println(first.hex());
        System.out.println(second.hex());

        try {
            System.out.println(LongNumber.LongSub(first, second).getLongNumber().hex());
        } catch (SubtractionException e) {
            System.out.println(e.getMessage());
            try {
                System.out.println(LongNumber.LongSub(second, first).getLongNumber().hex());
            } catch (SubtractionException e1) {

            }
        }

        test1 = new BigInteger(first.hex(), 16);
        test2 = new BigInteger(second.hex(), 16);
        System.out.println(test1.subtract(test2).toString(16).toUpperCase());
        System.out.println("--------------------");

        //mul
        first.setRandomValues();
        second.setRandomValues();

        System.out.println(first.hex());
        System.out.println(second.hex());

        System.out.println(LongNumber.LongMul(first, second).hex());

        test1 = new BigInteger(first.hex(), 16);
        test2 = new BigInteger(second.hex(), 16);
        System.out.println(test1.multiply(test2).toString(16).toUpperCase());
        System.out.println("--------------------");

        System.out.println(LongNumber.Karatsuba(first, second).hex());

        System.out.println("----------GCD----------");

        //GCD
        for (int i = 0; i < 25; ++i) {
            first.setRandomValues();
            second.setRandomValues();

            System.out.println(first.hex());
            System.out.println(second.hex());
            try {
                System.out.println(LongNumber.GCD(first, second).hex());
            } catch (SubtractionException e) {
                System.out.println(e.getMessage());
            }

            test1 = new BigInteger(first.hex(), 16);
            test2 = new BigInteger(second.hex(), 16);
            System.out.println(test1.gcd(test2).toString(16));
        }

        /*first.setRandomValues();
        second = new LongNumber(10);
        second.setRandomValues();
        System.out.println(first.hex());
        System.out.println(second.hex());
        try {
            System.out.println(LongNumber.BarrettReduction(first, second, LongNumber.getM(second)).hex());
        } catch (SubtractionException e ) {
            System.out.println(e.getMessage());
        }

        test1 = new BigInteger(first.hex(), 16);
        test2 = new BigInteger(second.hex(), 16);
        System.out.println(test1.mod(test2).toString(16));*/


        //pow
        LongNumber t1 = new LongNumber(5);
        LongNumber t2 = new LongNumber(3);
        t1.setRandomValues();
        t2.setRandomValues();

        System.out.println(t1.hex());
        System.out.println(t2.hex());

        System.out.println(LongNumber.LongPower1(t1, t2).hex());

        test1 = new BigInteger(t1.hex(), 16);

        System.out.println(test1.pow(Integer.parseInt(t2.hex(), 16)).toString(16));
    }

}
