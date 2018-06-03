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

    /**
     * Sign.
     * Uses Sha256 as hash function.
     * Sign = hash(message || random sequence) = H. if H is square left mod n, than find any root and forms sign:
     * (Message, Random Sequence, root).
     *
     * @param message BigInteger message to sign
     * @param randomSequenceLength length of random sequence
     * @param random random which uses to generate sequence
     * @return Object array with size = 3
     */
    public Object[] sign(BigInteger message, int randomSequenceLength, Random random) {
        Sha256 sha256 = Sha256.getInstance();
        StringBuilder concatenation;
        BigInteger hashBI;
        String randomSequence;

        do {
            concatenation = new StringBuilder();
            concatenation.append(message.toString(16));
            randomSequence = randomSequence(randomSequenceLength, random);
            concatenation.append(randomSequence);
            String hash = Sha256.bytesToHex(sha256.digest(concatenation.toString().getBytes()));

            hashBI = new BigInteger(hash, 16);
            hashBI = hashBI.mod(n);

        } while (!check(hashBI));

        BigInteger[] sqrtP = sqrtByPrimeMod(hashBI, p),
                sqrtQ = sqrtByPrimeMod(hashBI, q);
        BigInteger root = (sqrtP[0].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[0].multiply(p).multiply(p.modInverse(q))).mod(n);

        Object[] result = new Object[3];
        result[0] = message;
        result[1] = randomSequence;
        result[2] = root;

        return result;
    }

    private boolean check(BigInteger hash) {
        if (hash.modPow((p.subtract(BigInteger.ONE)).divide(TWO), p).compareTo(BigInteger.ONE) == 0) {
            if (hash.modPow((q.subtract(BigInteger.ONE)).divide(TWO), q).compareTo(BigInteger.ONE) == 0) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * Checks sign:
     * Calculates H = hash(Message || random sequence) and than check if root ^ 2 = H mod n.
     *
     * @param sign Object array which contains 3 parameters (Message, random sequence, root)
     * @throws MessageException if input array size is not 3
     * @throws MessageException if sign is not correct
     * @return true if sigh is correct
     */
    public boolean checkSign(Object[] sign, RabinClient user) throws MessageException {
        if (sign.length != 3) {
            throw new MessageException("sign check must take 3 parameters");
        }


        Sha256 sha256 = Sha256.getInstance();
        String hashInput = ((BigInteger) sign[0]).toString(16);
        hashInput += (String) sign[1];
        String hash = Sha256.bytesToHex(sha256.digest(hashInput.toString().getBytes()));

        BigInteger root = (BigInteger) sign[2];

        if (root.pow(2).mod(user.getPublicKey()).compareTo(new BigInteger(hash, 16).mod(user.getPublicKey())) == 0) {
            return true;
        } else {
            throw new MessageException("hash = " + hash + ", message = " + ((BigInteger) sign[0]).toString(16) + ", sequence = " + (String) sign[1] +
                    ", root = " + ((BigInteger) sign[2]).toString(16) + ", root ^ 2 = " + root.pow(2).mod(user.getPublicKey()).toString(16) +
                    ", hash mod n = " + new BigInteger(hash, 16).mod(user.getPublicKey()).toString(16));
        }

//        return root.pow(2).mod(user.getPublicKey()).compareTo(new BigInteger(hash, 16).mod(user.getPublicKey())) == 0;
    }

    /**
     * Blind Signature.
     *
     * Calculates sqrt of message.
     *
     * @param message message to sign
     * @return array of message sqtrs
     * */
    public BigInteger[] blindSign(BigInteger message) {
        BigInteger[] sqrtP = sqrtByPrimeMod(message, p),
                sqrtQ = sqrtByPrimeMod(message, q);
        BigInteger[] result = new BigInteger[4];

        result[0] = (sqrtP[0].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[0].multiply(p).multiply(p.modInverse(q))).mod(n);
        result[1] = (sqrtP[0].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[1].multiply(p).multiply(p.modInverse(q))).mod(n);
        result[2] = (sqrtP[1].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[0].multiply(p).multiply(p.modInverse(q))).mod(n);
        result[3] = (sqrtP[1].multiply(q).multiply(q.modInverse(p))).add(sqrtQ[1].multiply(p).multiply(p.modInverse(q))).mod(n);
        return result;
    }

    /**
     * Generates random sequence with specified length.
     *
     * @param length count of bits in sequence
     * @param random random which uses to generate sequence
     * @return String sequence of {0, 1}
     */
    private static String randomSequence(int length, Random random) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            result.append(random.nextInt(2));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Random random = new Random();
        int count = 0;

        do {
            ++count;
            RabinClient client = new RabinClient(512, random);

            BigInteger message = new BigInteger("329041902348920", 16);
            Object[] sign = client.sign(message, 54, random);
            RabinClient client2 = new RabinClient(123, random);
            try {
                System.out.println(client2.checkSign(sign, client));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (count <= 20);

    }

}
