package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * Add here also:)
 *
 */
public class LehmerHighGenerator extends LehmerLowGenerator {

    public LehmerHighGenerator(int startValue) {
        super(startValue);
    }

    public LehmerHighGenerator() {
        super();
    }

    @Override
    public void toFile(String fileName, int byteCount) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < byteCount; i++) {
                x0 = ((a * x0) + c) % m;
                StringBuilder result = new StringBuilder(Long.toBinaryString(x0));
                while (result.length() > 32) {
                    result.delete(0, 1);
                }
                while (result.length() < 32) {
                    result.insert(0, "0");
                }
                writer.write(result.substring(0, 8));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
