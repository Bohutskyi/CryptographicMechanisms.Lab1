package lab2.generators;

import java.util.Random;

/**
 * 6. Wolfram Generator.
 *
 * Start value r0 != 0 and sets randomly.
 * Output sequence {xi}: xi = ri mod 2, ri+1 = (r <<< 1) xor (ri v (ri >>> 1)).
 */
public class WolframGenerator extends Generator {

    private int r0;

    public WolframGenerator(int startValue) {
        r0 = startValue;
    }

    public WolframGenerator() {
        r0 = Math.abs(new Random().nextInt());
    }

    @Override
    public int getNext() {
        int x = r0 % 2;
        r0 = Integer.rotateLeft(r0, 1) ^ (r0 | (Integer.rotateRight(r0, 1)));
        return Math.abs(x);
    }

}
