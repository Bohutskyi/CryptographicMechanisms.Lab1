package lab3;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Rabin cryptosystem.
 *
 * Based on using Blum numbers. It means that in our implementation program generates 2 prime numbers p = 3 (mod 4) and
 * q = 3 (mod 4). And than n = p*q.
 *
 * For encrypting there is 2 restriction:
 * 1. Message value must be less than n.
 * 2. Message value must be greater than sqrt(n).
 * Encryption also uses 2 int values: b1 for parity of message number, b2 for Jacobi symbol.
 * These values also uses for decryption.
 */
public class RabinClient {

    private static final BigInteger TWO = new BigInteger("2", 10);
    private static final BigInteger THREE = new BigInteger("3", 10);
    private static final BigInteger FOUR = new BigInteger("4", 10);
    private static final BigInteger FIVE = new BigInteger("5", 10);
    private static final BigInteger SEVEN = new BigInteger("7", 10);
    private static final BigInteger EIGHT = new BigInteger("8", 10);

    private BigInteger q, p, n;

    /**
     * Constructor in which p and q are generated.
     * Also multiplies n = p*q.
     *
     * @param bitCount number of bits in q and p
     * @param random random which is used to generation
     */
    public RabinClient(int bitCount, Random random) {
        q = randomPrime(bitCount, random);
        do {
            p = randomPrime(bitCount, random);
        } while (q.compareTo(p) == 0);
        n = q.multiply(p);
    }

    /**
     * Getter for public key for encryption.
     *
     * @return n which is public key of current user
     */
    public BigInteger getPublicKey() {
        return n;
    }

    /**
     * Encryption.
     * Description is higher in class description.
     *
     * @param message BigInteger which is message for encryption
     * @return 3 values: BigInteger cipher text and 2 int values
     * @throws MessageException in case if restriction are disturbed
     */
    public Object[] encrypt(BigInteger message) throws MessageException {
        if (message.compareTo(n) == 1) {
            throw new MessageException("message is bigger than n: " + n.toString(16) + " < " + message.toString(16));
        } else if (message.compareTo(sqrt(n)) == -1 ) {
            throw new MessageException("message is smaller than sqrt(n): " + sqrt(n).toString(16) + " > " + message.toString(16));
        }

        int b1, b2;
        if (isEven(message)) {
            b1 = 0;
        } else {
            b1 = 1;
        }

        b2 = Jacobi(message, n);
        Object[] result = new Object[3];
        result[0] = message.modPow(TWO, n);
        result[1] = b1;
        result[2] = b2;

        return result;
    }

    /**
     * Decryption.
     *
     * Based on using cipher text and 2 values to get 1 true message from 4 candidates.
     *
     * @param cipherText 3 values: BigInteger cipher text and 2 int values
     * @return BigInteger message as decrypted cipher text
     * */
    public BigInteger decrypt(Object[] cipherText) {
        BigInteger[] sqrtP = sqrtByPrimeMod((BigInteger) cipherText[0], p),
                sqrtQ = sqrtByPrimeMod((BigInteger) cipherText[0], q);
//        BigInteger[] result = new BigInteger[4];
        BigInteger result;
        int b1 = (int) cipherText[1];
        int b2 = (int) cipherText[2];

        result = (sqrtP[0].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[0].multiply(p).multiply(p.modInverse(q))).mod(n);
        result = checkCandidate(result, b1, b2);
        if (result != null) {
            return result;
        }

        result = (sqrtP[0].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[1].multiply(p).multiply(p.modInverse(q))).mod(n);
        result = checkCandidate(result, b1, b2);
        if (result != null) {
            return result;
        }

        result = (sqrtP[1].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[0].multiply(p).multiply(p.modInverse(q))).mod(n);
        result = checkCandidate(result, b1, b2);
        if (result != null) {
            return result;
        }

        result = (sqrtP[1].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[1].multiply(p).multiply(p.modInverse(q))).mod(n);
        result = checkCandidate(result, b1, b2);
        if (result != null) {
            return result;
        }

        return result;
    }

    /**
     * Checks candidate using 2 values: parity and Jacobi symbol.
     *
     * @param candidate BigInteger as candidate for message
     * @param b1 parity
     * @param b2 Jacobi value
     * @return candidate in case it successfully passed the test and null else
     */
    private BigInteger checkCandidate(BigInteger candidate, int b1, int b2) {
        int tempB;
        if (isEven(candidate)) {
            tempB = 0;
        } else {
            tempB = 1;
        }
        int jacob = Jacobi(candidate, n);
        if (tempB == b1 && jacob == b2) {
            return candidate;
        }
        return null;
    }


    public static BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }

    private static BigInteger[] sqrtByPrimeMod(BigInteger number, BigInteger p) {
        BigInteger k = p.add(BigInteger.ONE).divide(FOUR);
            BigInteger[] result = new BigInteger[2];
            result[0] = number.modPow(k ,p);
            result[1] = result[0].negate();
            return result;
    }

    /**
     * Generates random prime BigInteger.
     *
     * @param bitsLength number of bits in generated number
     * @param random random which is used to generation
     * @return random prime BigInteger
     */
    private static BigInteger randomPrime(int bitsLength, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(bitsLength - 1, random);
            result = result.multiply(FOUR).add(THREE);
        } while (!result.isProbablePrime(50));
        return result;
    }

    /**
     * Checks if specified BigInteger is even.
     *
     * @param number BigInteger to check
     * @return true if number is even and false else
     */
    public static boolean isEven(BigInteger number) {
        return number.getLowestSetBit() != 0;
    }

    /**
     * Calculates Jacobi symbol.
     */
    public static int Jacobi(BigInteger number, BigInteger n) {
        int result = 0;

        if (number.compareTo(BigInteger.ZERO) == 0)
            result = (n.compareTo(BigInteger.ONE) == 0) ? 1 : 0;
        else if (number.compareTo(TWO) == 0) {

            BigInteger temp = n.mod(EIGHT);
            if (temp.compareTo(BigInteger.ONE) == 0 || temp.compareTo(SEVEN) == 0) {
                result = 1;
            }

            if (temp.compareTo(THREE) == 0 || temp.compareTo(FIVE) == 0) {
                result = -1;
            }

        }
        else if (number.compareTo(n) != -1)
            result = Jacobi(number.mod(n), n);
        else if (number.mod(TWO).compareTo(BigInteger.ZERO) == 0 )
            result = Jacobi(TWO, n) * Jacobi(number.divide(TWO), n);
        else
            result = ( number.mod(FOUR).compareTo(THREE) == 0 && n.mod(FOUR).compareTo(THREE) == 0 ) ? -Jacobi(n, number) : Jacobi(n, number);
        return result;
    }

//    public static void main(String[] args) {
//        RabinClient client = new RabinClient(7, new SecureRandom());
//        System.out.println(client.p.toString(2));
//        System.out.println(client.p.isProbablePrime(50));
//        System.out.println(client.q.toString(2));
//        System.out.println(client.q.isProbablePrime(50));
//        System.out.println("----------");
//
////
//        BigInteger message = new BigInteger("908", 10);
//        System.out.println("n = " + client.getPublicKey().toString(10));
//        System.out.println("m = " + message.toString(10));
//        try {
//            Object[] cipher = client.encrypt(message);
//            System.out.println("cipher = " + ((BigInteger)cipher[0]).toString(10));
//            System.out.println("b1 = " + ((int) cipher[1]));
//            System.out.println("b2 = " + ((int) cipher[2]));
//            System.out.println("------------");
//            BigInteger decr = client.decrypt(cipher);
//            System.out.println(decr.toString(10));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
    
}
