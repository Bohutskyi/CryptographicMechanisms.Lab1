package lab2.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 5. Генератор Джиффі - – потоковий шифр, що генерує шифруючу гаму за рахунок нелінійної комбінації трьох
 * лінійних регістрів зсуву.
 * Регістр L11: x11 = x0 + x2;
 * Регістр L9: y9 = y0 + y1 + y3 + y4;
 * Регістр L10: s10 = s0 + s3.
 * Вихідна послідовність бітів обчислюється за правилом z(i) = si * xi + (1 + si) yi
 * */
public class GeffeGenerator {

//    private static Random random = new Random();

    private class LinearFeedbackShiftRegister1 {
        private int vector;//' = random.nextInt();
        private static final int n = 11;

        public void setVector(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }

        public int getCurrentBit() {
            return vector & 1;
        }

        public int getNext() {
            int temp = ((vector >> 2) & 1) ^ ((vector) & 1);
            temp = temp & 1;
            vector = vector >> 1;
            vector = (vector ^ (temp << (n - 1)));
            return vector;
        }
    }

    private class LinearFeedbackShiftRegister2 {
        private int vector;// = random.nextInt();
        private static final int n = 9;

        public void setVector(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }

        public int getCurrentBit() {
            return vector & 1;
        }

        public long getNext() {
            int temp = ((vector >> 4) & 1) ^ ((vector >> 3) & 1) ^ ((vector >> 1) & 1) ^ ((vector) & 1);
            temp = temp & 1;
            vector = vector >> 1;
            vector = (vector ^ (temp << (n - 1)));
            return vector;
        }
    }

    private class LinearFeedbackShiftRegister3 {
        private int vector;// = random.nextInt();
        private static final int n = 10;

        public void setVector(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }

        public int getCurrentBit() {
            return vector & 1;
        }

        public long getNext() {
            int temp = ((vector >> 3) & 1) ^ ((vector) & 1);
            temp = temp & 1;
            vector = vector >> 1;
            vector = (vector ^ (temp << (n - 1)));
            return vector;
        }
    }

    private LinearFeedbackShiftRegister1 l1 = new LinearFeedbackShiftRegister1();
    private LinearFeedbackShiftRegister2 l2 = new LinearFeedbackShiftRegister2();
    private LinearFeedbackShiftRegister3 l3 = new LinearFeedbackShiftRegister3();

    public GeffeGenerator(int startValue1, int startValue2, int startValue3) {
        l1.setVector(startValue1);
        l2.setVector(startValue2);
        l3.setVector(startValue3);
    }

    private int getNext() {
        l1.getNext();
        l2.getNext();
        l3.getNext();
        if (l3.getCurrentBit() == 1) {
            return l1.getCurrentBit();
        } else {
            return l2.getCurrentBit();
        }
    }

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

}
