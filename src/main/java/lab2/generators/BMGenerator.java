package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * 8. Генератор BM (Блюма-Мікалі)
 */
public class BMGenerator {

    protected static final BigInteger p = new BigInteger("0CEA42B987C44FA642D80AD9F51F10457690DEF10C83D0BC1BCEE12FC3B6093E3", 16);
    protected static final BigInteger a = new BigInteger("5B88C41246790891C095E2878880342E88C79974303BD0400B090FE38A688356", 16);
    protected static final BigInteger threshold = p.subtract(new BigInteger("1")).divide(new BigInteger("2"));

    protected BigInteger T0;// = new BigInteger(Integer.toString(Math.abs(new Random().nextInt())));

    public BMGenerator(int startValue) {
        this.T0 = new BigInteger(Integer.toString(startValue), 10);
    }

    protected void nextIteration() {
        T0 = power(a, T0, p);
    }

    public void toFile(String fileName, int length) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < length; i++) {
                if (i % 50000 == 0) {
                    System.out.println("i = " + i);
                }
                nextIteration();
                if (T0.compareTo(threshold) == -1) {
                    writer.write("1");
                } else {
                    writer.write("0");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*protected BigInteger power(BigInteger a, BigInteger power) {
        final BigInteger two = new BigInteger("2");
        BigInteger result = BigInteger.ONE;
        while (power.compareTo(BigInteger.ZERO) != 0) {
            if (power.remainder(two).compareTo(BigInteger.ZERO) == 0) {
                power = power.divide(two);
                a = a.multiply(a);
            }
            else {
                power = power.subtract(BigInteger.ONE);
                result = result.multiply(a);
            }
        }
        return result;
    }*/

    protected BigInteger power(BigInteger a, BigInteger power, BigInteger mod) {
        final BigInteger two = new BigInteger("2");
        BigInteger result = BigInteger.ONE;
        while (power.compareTo(BigInteger.ZERO) != 0) {
            if (power.remainder(two).compareTo(BigInteger.ZERO) == 0) {
                power = power.divide(two);
                a = a.multiply(a).mod(mod);
            }
            else {
                power = power.subtract(BigInteger.ONE);
                result = (result.multiply(a)).mod(mod);
            }
        }
        return result;
    }

}
