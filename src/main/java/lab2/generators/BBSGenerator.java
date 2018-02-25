package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * 9. Генератор BBS
 */
public class BBSGenerator {

    protected static final BigInteger p = new BigInteger("0D5BBB96D30086EC484EBA3D7F9CAEB07", 16);
    protected static final BigInteger q = new BigInteger("425D2B9BFDB25B9CF6C416CC6E37B59C1F", 16);
    protected static final BigInteger n = p.multiply(q);
    protected static BigInteger r0 = new BigInteger(Long.toString(Math.abs(new Random().nextLong() + 2)));
    protected static BigInteger x;

    protected static void nextIteration() {
        r0 = (r0.multiply(r0)).mod(n);
        x = r0.mod(new BigInteger("2"));
    }

    public static void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                nextIteration();
                String temp = x.toString(2);
                writer.write(temp.charAt(temp.length() - 1));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
