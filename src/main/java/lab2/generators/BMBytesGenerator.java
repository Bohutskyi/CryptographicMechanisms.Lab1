package lab2.generators;

import additional.Mathematics;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/**
 * генератор BM_bytes (байтова модифікація генератору Блюма-Мікалі)
 * and here add:)
 */
public class BMBytesGenerator extends BMGenerator {

    protected static final BigInteger threshold = p.subtract(new BigInteger("1")).divide(new BigInteger("256"));

    public BMBytesGenerator(int startValue) {
        super(startValue);
    }

    public BMBytesGenerator() {
        super();
    }

    @Override
    public int getNext() {
        T0 = power(a, T0, p);
        T0 = T0.multiply(new BigInteger("256", 10)).divide(p.subtract(BigInteger.ONE));
        return T0.intValue();
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

    /*public void toFile(String fileName, int startValue, int byteLength) {
        T0 = new BigInteger(Integer.toString(startValue));
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < byteLength; i++) {
//                if (i % 25000 == 0) {
//                    System.out.println("i = " + i);
//                }
                nextIteration();

                for (int j = 0; j <= 127; j++) {
                    if (T0.compareTo(threshold.multiply(new BigInteger(Integer.toString(j)))) == 1 && T0.compareTo(threshold.multiply(new BigInteger(Integer.toString(j + 1)))) == -1) {
                        writer.write(Integer.toString(j) + " ");
                    }
                }

            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }*/

}
