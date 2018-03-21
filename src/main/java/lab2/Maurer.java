package lab2;

import java.math.BigInteger;
import java.util.Random;

public class Maurer {

    private static final BigInteger TWO = new BigInteger("2", 10);

    //Some literature
    //http://citeseerx.ist.psu.edu/viewdoc/download;jsessionid=CED9834DC06CE9F32FF3F688E146808B?doi=10.1.1.26.2151&rep=rep1&type=pdf
    //https://code.msdn.microsoft.com/windowsapps/Maurers-Algorithm-for-3422d1b2
    //https://pdfs.semanticscholar.org/3df0/e64897ebbed46a6d034196f9b3962ca45b07.pdf
    /**
     * Generates probable prime number with specified number of bits.
     *
     * @param k number of bits
     * @param L number for prime check
     * @param M parameter that guarantees the existence of the prime number. Recommend M = 20
     * @return Probable prime BigInteger
     */
    public static BigInteger MaurerGeneration(int k, final int L, final int M) {
        Random random = new Random();

        if (k <= 20) {
            BigInteger n;
            do {
                n = new BigInteger(k, random);
            } while (!n.isProbablePrime(L));
            return n;
        }

//        int m = 20;
        double r;

        if (k > 2 * M) {
            do {
                double s = random.nextDouble();
                r = Math.pow(2, s - 1);
            } while (k - k * r <= M);
        } else {
            r = 0.5;
        }

        BigInteger q = MaurerGeneration((int) Math.floor(r * k), L, M);

        //I = [2 ^ k+1 / 2*q]
        BigInteger I = TWO.pow(k - 1);
        BigInteger temp = TWO.multiply(q);
        I = I.divide(temp);

        boolean success = false;

        BigInteger n = null;

        while (!success) {

            BigInteger R;

            do {
                //R ∈ [I + 1, 2I]
                R = randomBigInteger(I, random);
                R = R.add(I);

                //n = 2Rq + 1
                n = TWO.multiply(R).multiply(q).add(BigInteger.ONE);
            } while (!n.isProbablePrime(L));

            //a ∈ [2, n − 2]
//            BigInteger a = randomBigInteger(n.subtract(BigInteger.ONE), random).add(BigInteger.ONE);
            BigInteger a = randomBigInteger(n.subtract(BigInteger.ONE), random);

            //b = a ^ n−1 mod n
            BigInteger b = a.modPow(n.subtract(BigInteger.ONE), n);

            if (b.compareTo(BigInteger.ONE) == 0) {
                b = TWO.modPow(TWO.multiply(R), n);
                BigInteger d = n.gcd(b.subtract(BigInteger.ONE));
                if (d.compareTo(BigInteger.ONE) == 0) {
                    success = true;
                }
            }
        }
        return n;
    }

    private static BigInteger randomBigInteger(BigInteger number, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(number.bitLength(), random);
        } while (result.compareTo(number) != -1);
        return result;
    }

}
