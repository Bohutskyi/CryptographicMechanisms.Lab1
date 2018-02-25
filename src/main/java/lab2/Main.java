package lab2;

import java.math.BigInteger;
import java.util.Random;

public class Main {

    private static final String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static void main(String[] args) {
        BigInteger temp = generatePrime(100);
        System.out.println(temp.toString(16));
        System.out.println(temp.isProbablePrime(50));
    }

    static BigInteger generateValue(int length) {
        StringBuilder temp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            temp.append(data[random.nextInt(15)]);
        }
        return new BigInteger(temp.toString(), 16);
    }

    static BigInteger generatePrime(int length) {
        BigInteger temp;
        do {
            temp = generateValue(length);
        } while (!MillerRabinTest.isPrime(temp, 50));
        return temp;
    }

}
