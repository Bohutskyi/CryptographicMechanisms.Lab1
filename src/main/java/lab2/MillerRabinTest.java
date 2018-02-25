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
        } while(result.compareTo(n) >= 0 && result.compareTo(BigInteger.ONE) < 0);

        return result;
    }

    public static boolean isPrime(BigInteger p, int k) {
        int s = 0;
        BigInteger d;
        for (d = p.subtract(BigInteger.ONE); d.mod(MillerRabinTest.BIGINTEGERCONST.TWO).equals(BigInteger.ZERO); ++s) {
            d = d.divide(MillerRabinTest.BIGINTEGERCONST.TWO);
        }

        for (int i = 0; i < k; i++) {
            BigInteger x = randomBigInteger(p);
            if(!GCD.gcd(x, p).getGcd().equals(BigInteger.ONE)) {
                return false;
            }

            BigInteger temp = x.modPow(d, p);
            if (!temp.equals(BigInteger.ONE) && !temp.equals(p.subtract(BigInteger.ONE))) {
                int r;
                for(r = 1; r < s; ++r) {
                    temp = temp.modPow(MillerRabinTest.BIGINTEGERCONST.TWO, p);
                    if(temp.equals(p.subtract(BigInteger.ONE))) {
                        break;
                    } else if(temp.equals(BigInteger.ONE)) {
                        return false;
                    }
                }
                if(r == s) {
                    return false;
                }
            }
        }
        return true;
    }

//    public static void main(String[] args) {
//        for(int i = 10; i < 4000; ++i) {
//            BigInteger temp = (new BigInteger("2")).pow(i).subtract(BigInteger.ONE);
//            if(isPrime(temp, 10)) {
//                System.out.println(temp.toString());
//            }
//        }
//        System.out.println(isPrime(new BigInteger("0FA28BA05169E7EAF69A15C952969B14B0072025DABF19F31DE0C0B691083596D35FD39A1B46148D5F5F7E90A362AE1DC29FFEA859D2D452EE5A2D53130A57FD28207BA7A6B29B14F8056FAAA2A72367AAB40BE2E322659B3A98CD8693485A79AE5F4153E0309220D3B680100FC8738832B4DFF7DD8653DA257C60F18BF7C9E2F", 16), 20));
//    }
}
