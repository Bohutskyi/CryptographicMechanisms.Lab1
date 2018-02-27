package lab2;

import java.math.BigInteger;
import java.util.Random;

public class Generation {

    private static final String[] data = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static BigInteger generateValue(int length) {
        StringBuilder temp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            temp.append(data[random.nextInt(16)]);
        }
        return new BigInteger(temp.toString(), 16);
    }

    public static BigInteger generatePrime(int length) {
        BigInteger temp;
        do {
            temp = generateValue(length);
        } while (!MillerRabinTest.isPrime(temp, 50));
        return temp;
    }

//    static BigInteger MaurerGeneration(int length) {
//
//    }

    public static void main(String[] args) {
        short s1 = 2311;
        short s2 = 423;

        System.out.println((short)(s1+s2));
        System.out.println((short)(s1*s2));
    }

}
