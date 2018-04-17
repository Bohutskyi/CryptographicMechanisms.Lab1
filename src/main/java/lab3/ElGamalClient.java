package lab3;

import java.math.BigInteger;
import java.util.Random;

public final class ElGamalClient {

    private BigInteger k;
    private int lenght;

    //publicKey = {p, a, y}
    private BigInteger[] publicKey;

    public ElGamalClient(int bitsNumber) {
        lenght = bitsNumber;
        publicKey = new BigInteger[3];
        Random random = new Random();
        publicKey[0] = randomPrime(bitsNumber, random);
        k = randomBigInteger(bitsNumber, publicKey[0], random);
        publicKey[1] = randomBigInteger(bitsNumber, publicKey[0], random);
        publicKey[2] = publicKey[1].modPow(k, publicKey[0]);
    }

    public BigInteger[] getPublicKey() {
        return this.publicKey;
    }

    public BigInteger[] encrypt(BigInteger M) {
        if (M.compareTo(publicKey[0]) == -1) {
            return null;
        }
        BigInteger x = new BigInteger(lenght, new Random());
        BigInteger[] result = new BigInteger[2];
        result[0] = publicKey[1].modPow(x, publicKey[0]);
        result[1] = publicKey[2].modPow(x, publicKey[0]);
        result[1] = result[1].multiply(M).mod(publicKey[0]);
        return result;
    }

    private static BigInteger randomBigInteger(int bitsLength, BigInteger value, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(bitsLength, random);
        } while (result.compareTo(value) != -1);
        return result;
    }

    private static BigInteger randomPrime(int bitsLength, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(bitsLength, random);
        } while (!result.isProbablePrime(50));
        return result;
    }

    public static void main(String[] args) {
        ElGamalClient client = new ElGamalClient(65);
        for (BigInteger i : client.getPublicKey()) {
            System.out.println(i.toString(10));
        }
        BigInteger message = new BigInteger("123456789", 10);
        BigInteger[] enc = client.encrypt(message);

    }

}
