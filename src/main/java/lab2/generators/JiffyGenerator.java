package lab2.generators;

import java.util.Random;

/**
 * 5. Generator Jiffy.
 * Register L11: x11 = x0 + x2;
 * Register L9: y9 = y0 + y1 + y3 + y4;
 * Register L10: s10 = s0 + s3.
 *
 * Output sequence {zi} z(i) = si * xi + (1 + si) yi.
 *
 * Start register's values sets randomly, but not equivalent zero.
 */
public class JiffyGenerator extends Generator {

    private class LinearFeedbackShiftRegister1 {
        private int vector;
        private static final int n = 11;

        void setVector(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }

        int getCurrentBit() {
            return vector & 1;
        }

        int getNext() {
            int temp = ((vector >> 2) & 1) ^ ((vector) & 1);
            temp = temp & 1;
            vector = vector >> 1;
            vector = (vector ^ (temp << (n - 1)));
            return vector;
        }
    }

    private class LinearFeedbackShiftRegister2 {
        private int vector;
        private static final int n = 9;

        void setVector(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }

        int getCurrentBit() {
            return vector & 1;
        }

        long getNext() {
            int temp = ((vector >> 4) & 1) ^ ((vector >> 3) & 1) ^ ((vector >> 1) & 1) ^ ((vector) & 1);
            temp = temp & 1;
            vector = vector >> 1;
            vector = (vector ^ (temp << (n - 1)));
            return vector;
        }
    }

    private class LinearFeedbackShiftRegister3 {
        private int vector;
        private static final int n = 10;

        void setVector(int vector) {
            this.vector = vector;
        }

        public int getVector() {
            return vector;
        }

        int getCurrentBit() {
            return vector & 1;
        }

        long getNext() {
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

    /**
     * Create generator with specified start values.
     */
    public JiffyGenerator(int startValue1, int startValue2, int startValue3) {
        l1.setVector(startValue1);
        l2.setVector(startValue2);
        l3.setVector(startValue3);
    }

    /**
     * In this case start values will be random.
     * */
    public JiffyGenerator() {
        Random random = new Random();
        l1.setVector(Math.abs(random.nextInt()));
        l2.setVector(Math.abs(random.nextInt()));
        l3.setVector(Math.abs(random.nextInt()));
    }

    @Override
    public int getNext() {
        l1.getNext();
        l2.getNext();
        l3.getNext();
        if (l3.getCurrentBit() == 1) {
            return l1.getCurrentBit();
        } else {
            return l2.getCurrentBit();
        }
    }

}
