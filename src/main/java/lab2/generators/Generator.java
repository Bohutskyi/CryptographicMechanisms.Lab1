package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Abstract class for generators.
 *
 * It contains 2 methods.
 *
 * Example of use any generator which extends this class.
 *
 * BBSGenerator generator = new BBSGenerator();
 * generator.toFile("temp", 10);
 *
 * or
 *
 * BBSGenerator generator = new BBSGenerator();
 * for (int i = 0; i < 15; ++i) {
 *  System.out.print(generator.getNext());
 * }
 *
 * Here instead of BBSGenerator can be any class that extends Generator.
 * */
public abstract class Generator {

    /**
     * Write random values to file.
     *
     * @param fileName file destination where values would be written.
     * @param length count of bits.
     * */
    public void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < length; i++) {
                writer.write(Integer.toString(getNext()));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get next generator value.
     */
    public abstract int getNext();

}
