package additional;

import java.math.BigInteger;

public class Mathematics {

    public static BigInteger power(BigInteger a, BigInteger power, BigInteger mod) {
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

    /**
     * Gets binary of specified value and adds zeros to beginning to get all 8 bits.
     *
     * @param value value which binary need to get
     * @return binary of value
     * */
    public static String getBinary(int value) {
        StringBuilder temp = new StringBuilder(Integer.toBinaryString(value));
        while (temp.length() < 8) {
            temp.insert(0, "0");
        }
        return temp.toString();
    }
}
