package lab2.generators;

import additional.Mathematics;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/**
 * 9.2. Generator BBS - Blum Blum Shub.
 * Bytes implementation.
 *
 * Everything is same as in BBS, but xi = ri mod 256.
 */
public class BBSGeneratorByBytes extends BBSGenerator {

    @Override
    public int getNext() {
        r0 = (r0.multiply(r0)).mod(n);
        x = r0.mod(new BigInteger("256", 10)).intValue();
        return x;
    }

    @Override
    public void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < length; i++) {
                writer.write(Mathematics.getBinary(getNext()));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
