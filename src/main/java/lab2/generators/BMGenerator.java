package lab2.generators;

import java.math.BigInteger;
import java.util.Random;

/**
 * 8. Blum-Micali Generator.
 *
 * It is proved that the ability to guess the bits of the output sequence of this generator is equivalent
 * to the possibility of solving the problem of discrete logarithm.
 * p - large prime number, a is primitive root of modulus p.
 * T0 is random not zero, 0 <= T0 <= p - 1.
 * T(i+1) = a ^ T(i) mod p.
 * {x(i)}: x(i) = 1 if T < (p-1)/2; 0 else.
 */
public class BMGenerator extends Generator {

    protected static final BigInteger p = new BigInteger("0CEA42B987C44FA642D80AD9F51F10457690DEF10C83D0BC1BCEE12FC3B6093E3", 16);
    protected static final BigInteger a = new BigInteger("5B88C41246790891C095E2878880342E88C79974303BD0400B090FE38A688356", 16);
    protected static final BigInteger threshold = p.subtract(new BigInteger("1")).divide(new BigInteger("2"));

    protected BigInteger T0;// = new BigInteger(Integer.toString(Math.abs(new Random().nextInt())));

    public BMGenerator(int startValue) {
        this.T0 = new BigInteger(Integer.toString(startValue), 10);
    }

    public BMGenerator() {
        this.T0 = new BigInteger(Integer.toString(Math.abs(new Random().nextInt())));
    }

    @Override
    public int getNext() {
        T0 = power(a, T0, p);
        if (T0.compareTo(threshold) == -1) {
            return 1;
        } else {
            return 0;
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
