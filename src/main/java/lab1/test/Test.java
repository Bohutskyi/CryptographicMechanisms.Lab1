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
    }

}
