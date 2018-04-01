package lab3;

import java.math.BigInteger;
import java.util.Random;

public class RabinClient {

    private static final BigInteger TWO = new BigInteger("2", 10);
    private static final BigInteger THREE = new BigInteger("3", 10);
    private static final BigInteger FOUR = new BigInteger("4", 10);
    private static final BigInteger FIVE = new BigInteger("5", 10);
    private static final BigInteger EIGTH = new BigInteger("8", 10);

    private BigInteger q, p, n;

    public RabinClient(int bitCount, Random random) {
        q = randomPrime(bitCount - 1, random);
        do {
            p = randomPrime(bitCount - 1, random);
        } while (q.compareTo(p) == 0);

//        q = q.multiply(TWO);
//        q = q.add(BigInteger.ONE);
//        p = p.multiply(TWO).add(BigInteger.ONE);

        n = q.multiply(p);
    }

    public BigInteger getPublicKey() {
        return n;
    }

    public BigInteger encrypt(BigInteger message) throws MessageException {
        if (message.compareTo(n) == 1) {
            throw new MessageException("message is bigger than n: " + n.toString(16) + " < " + message.toString(16));
        } else if (message.compareTo(sqrt(n)) == -1 ) {
            //WIP
//            throw new MessageException("message is smaller than sqrt(n): " + n.toString(16) + " < " + message.toString(16));
            throw new MessageException("WIP");
        }
        return message.modPow(TWO, n);
    }

    public BigInteger[] decrypt(BigInteger cipherText) {
        BigInteger bp1 = sqrt(cipherText);
        bp1 = bp1.mod(p);
        BigInteger bp2 = bp1.negate().add(p);
        BigInteger bq1 = sqrt(cipherText);
        bq1 = bq1.mod(q);
        BigInteger bq2 = bq1.negate().add(q);

        System.out.println("+++++++++++++++++");
        System.out.println(bp1.toString(10));
        System.out.println(bp2.toString(10));
        System.out.println(bq1.toString(10));
        System.out.println(bq2.toString(10));
        System.out.println("+++++++++++++++++");

        BigInteger[] result = new BigInteger[4];

        result[0] = (bp1.multiply(q).multiply(q.modInverse(p))).add(bq1.multiply(p).multiply(p.modInverse(q))).mod(n);
        result[1] = (bp1.multiply(q).multiply(q.modInverse(p))).add(bq2.multiply(p).multiply(p.modInverse(q))).mod(n);
        result[2] = (bp2.multiply(q).multiply(q.modInverse(p))).add(bq1.multiply(p).multiply(p.modInverse(q))).mod(n);
        result[3] = (bp2.multiply(q).multiply(q.modInverse(p))).add(bq2.multiply(p).multiply(p.modInverse(q))).mod(n);

//        result[1] = bp2.multiply(q).multiply(q.modInverse(p)).add(bq1).multiply(p).multiply(p.modInverse(q)).mod(n);
//        result[2] = bp1.multiply(q).multiply(q.modInverse(p)).add(bq2).multiply(p).multiply(p.modInverse(q)).mod(n);
//        result[3] = bp2.multiply(q).multiply(q.modInverse(p)).add(bq2).multiply(p).multiply(p.modInverse(q)).mod(n);

        return result;
    }

    private static BigInteger sqrt(BigInteger n) {
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
        if (p.mod(FOUR).compareTo(THREE) == 0) {
            BigInteger k = p.add(BigInteger.ONE).divide(FOUR);
            BigInteger[] result = new BigInteger[2];
            result[0] = number.modPow(k ,p);
            result[1] = result[0].negate();
            return result;
        }

        if (p.mod(new BigInteger("8", 10)).compareTo(new BigInteger("5" ,10)) == 0) {

        }


        return null;
    }

    private static BigInteger randomPrime(int bitsLength, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(bitsLength, random);
            result = result.multiply(TWO).add(BigInteger.ONE);
        } while (!result.isProbablePrime(50));
        return result;
    }

    public static void main(String[] args) {
        RabinClient client = new RabinClient(7, new Random());
//        System.out.println(client.q.toString(2));
//        System.out.println(client.q.isProbablePrime(50));
//        System.out.println(client.p.toString(2));
//        System.out.println(client.p.isProbablePrime(50));

        System.out.println("q = " + client.q.toString(10));
        System.out.println(client.q.mod(new BigInteger("4", 10)).toString(10));
        System.out.println(client.q.mod(new BigInteger("8", 10)).toString(10));
        System.out.println("p = " + client.p.toString(10));
        System.out.println(client.p.mod(new BigInteger("4", 10)).toString(10));
        System.out.println(client.p.mod(new BigInteger("8", 10)).toString(10));

        System.out.println("***************");

        BigInteger message = new BigInteger("723", 10);
        System.out.println("n = " + client.getPublicKey().toString(10));
        System.out.println("m = " + message.toString(10));
        try {
            BigInteger c = client.encrypt(message);
            System.out.println("cipher = " + c.toString(10));
            System.out.println("------------");
            for (BigInteger in : client.decrypt(c)) {
                System.out.println(in.toString(10));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        for (int i = 0; i < 25; ++i) {
//            i1 = new BigInteger(2000, random);
//            System.out.println(i1.toString(16));
//            System.out.println(sqrt(i1.pow(2)).toString(16));
//        }
    }


}
