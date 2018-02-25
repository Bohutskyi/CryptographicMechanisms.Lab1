package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 2. Linear congruent generator.
 *
 * x(n+1) = (a * x(n) + c) mod n.
 * a, m, c are const.
 * x(0) - random and not equivalent zero.
 * LehmerLow returns lower 8 bits of x(n).
 */
public class LehmerLowGenerator {

    protected static final long a = 65537;
    protected static final long m = 4294967296L;
    protected static final long c = 119;

    protected long x0;

    public LehmerLowGenerator(int startValue) {
        this.x0 = startValue;
    }

    public LehmerLowGenerator() {
        this.x0 = Math.abs(new Random().nextLong());
    }

    public void toFile(String fileName, int byteCount) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < byteCount; i++) {
                x0 = ((a * x0) + c) % m; // y0 = x0 & (0xFF);
                StringBuilder result = new StringBuilder(Long.toBinaryString(x0));
                while (result.length() < 8) {
                    result.insert(0, "0");
                }
                while (result.length() > 8) {
                    result.delete(0, 1);
                }
                writer.write(result.toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
