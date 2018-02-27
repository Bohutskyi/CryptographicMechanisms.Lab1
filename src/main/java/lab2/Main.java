package lab2;

import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        BigInteger temp = Generation.generatePrime(100);
        System.out.println(temp.toString(16));
        System.out.println(temp.isProbablePrime(50));
    }

}
