package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * 9.
 */
public class BBSGeneratorByBytes extends BBSGenerator {

    protected static void nextIteration() {
        r0 = (r0.multiply(r0)).mod(n);
        x = r0.mod(new BigInteger("256", 10));
    }

    public static void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            int count = 0;
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                nextIteration();
                StringBuilder temp = new StringBuilder(x.toString(2));
                while (temp.length() < 8) {
                    temp.insert(0, "0");
                }
                writer.write(temp.substring(temp.length() - 8));
                count += 8;
                if (count > length) {
                    break;
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
