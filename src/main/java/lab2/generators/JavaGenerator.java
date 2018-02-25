package lab2.generators;

import java.util.Random;

/**
 * 1. Build-in pseudo-random number Java generator.
 */
public class JavaGenerator extends Generator {

    private Random random;

    public JavaGenerator() {
        this.random = new Random();
    }

    @Override
        public int getNext() {
        return random.nextInt(2);
    }
}
