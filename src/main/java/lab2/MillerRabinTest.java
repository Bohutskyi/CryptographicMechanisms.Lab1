package lab2;

import additional.GCD;
import java.math.BigInteger;
import java.util.Random;

public class MillerRabinTest {

    private static class BIGINTEGERCONST {
        private static final BigInteger MINUSONE = new BigInteger("-1");
        private static final BigInteger TWO = new BigInteger("2");
    }

    public static BigInteger randomBigInteger(BigInteger n) {
        Random random = new Random();
        int bitLength = n.bitLength();

        BigInteger result;
        do {
            result = new BigInteger(bitLength, random);
        } while (result.compareTo(n) >= 0 && result.compareTo(BigInteger.ONE) < 0);

        return result;
    }

    public static boolean isProbablePrime(BigInteger p, int certainty) {
        if (p.compareTo(BigInteger.ONE) != 1) {
            return false;
        }

        if (certainty <= 0) {
            return true;
        }

        int s = 0;
        BigInteger d;
        for (d = p.subtract(BigInteger.ONE); d.mod(MillerRabinTest.BIGINTEGERCONST.TWO).equals(BigInteger.ZERO); ++s) {
            d = d.divide(MillerRabinTest.BIGINTEGERCONST.TWO);
        }

        for (int i = 0; i < certainty; i++) {
            BigInteger x = randomBigInteger(p);
            if (!GCD.gcd(x, p).getGcd().equals(BigInteger.ONE)) {
                return false;
            }

            BigInteger temp = x.modPow(d, p);
            if (!temp.equals(BigInteger.ONE) && !temp.equals(p.subtract(BigInteger.ONE))) {
                int r;
                for (r = 1; r < s; ++r) {
                    temp = temp.modPow(MillerRabinTest.BIGINTEGERCONST.TWO, p);
                    if (temp.equals(p.subtract(BigInteger.ONE))) {
                        break;
                    } else if (temp.equals(BigInteger.ONE)) {
                        return false;
                    }
                }
                if (r == s) {
                    return false;
                }
            }
        }
        return true;
    }

}