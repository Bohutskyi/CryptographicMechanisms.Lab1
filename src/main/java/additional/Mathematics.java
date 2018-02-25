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

}
