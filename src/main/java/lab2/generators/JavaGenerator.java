package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 1. Вбудований генератор псевдовипадкових чисел Java
 * 1. Build-in pseudo-random number Java generator
 *
 * Also need to add SecureRandom
 */

public class JavaGenerator {

    public static void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                writer.write(Integer.toString(random.nextInt(2)));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
