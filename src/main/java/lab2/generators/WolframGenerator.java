package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/**
 * 6. Генератор Вольфрама
 */
public class WolframGenerator {

    private int r0;

    public WolframGenerator(int startValue) {
        r0 = startValue;
    }

    public void toFile(String fileName, int bitCount) {
        int x = 0;
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < bitCount; i++) {
                x = r0 % 2;
                r0 = Integer.rotateLeft(r0, 1) ^ (r0 | (Integer.rotateRight(r0, 1)));

                writer.write(Integer.toString(Math.abs(x)));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


//    public static void toFile(long startValue, int bitCount, String fileName) {
//        r0 = startValue;
//        try {
//            FileWriter writer = new FileWriter(fileName);
//            for (int i = 0; i < bitCount; i++) {
//
//                writer.write();
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }

}
