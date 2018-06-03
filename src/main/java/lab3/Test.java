package lab3;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Test {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String DELIMITER = "=================================================================";

    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "R": {
                    RabinTest();
                } break;
                case "S": {
                    ShaTest();
                } break;
                case "RS": {
                    signRabinTest();
                } break;
                case "RSAB": {
                    blindSignRSATest();
                } break;
                case "RB": {
                    blindSignTest();
                } break;
                default: break;
            }
        }
    }

    //Test for Rabin system
    private static void RabinTest() {
        System.out.println(DELIMITER);
        printDate();
        System.out.println("Test for Rabin system was started.\n");

        SecureRandom random = new SecureRandom();
        long
                testCount = 0,
                failures = 0;

        for (int i = 100; i <= 1000; i += 100) {
            System.out.println(i);
            for (int j = 0; j < 5; ++j) {
                RabinClient client = new RabinClient(i, random);
                BigInteger message = generateMessage(client.getPublicKey(), i, random);
                try {
                    Object[] cipherText = client.encrypt(message);
                    ++testCount;
                    if (client.decrypt(cipherText).compareTo(message) != 0) {
                        ++failures;
                        System.out.println("Result don't match. Cipher = " + ((BigInteger)cipherText[0]).toString(16) +
                                ", decrypt results = " + client.decrypt(cipherText).toString(16));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        printDate();
        DecimalFormat format = new DecimalFormat("#.####");
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " (" + format.format((1. * failures / testCount)) + "%) failed.");
        System.out.println(DELIMITER);
    }

    //Test for Sha256
    private static void ShaTest() {
        System.out.println(DELIMITER);
        printDate();
        System.out.println("Test for Sha256 was started.\n");
        long
                testCount = 0,
                failures = 0;

        Sha256 sha256 = Sha256.getInstance();
        for (int i = 100; i <= 1000; i += 100) {
//            System.out.println(i);
            for (int j = 0; j < 20; ++j) {
                String input = randomString(i);
                try {
                    ++testCount;
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] systemHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                    byte[] personalHash = sha256.digest(input.getBytes());
                    if (Sha256.bytesToHex(systemHash).equals(Sha256.bytesToHex(personalHash))) {

                    } else {
                        ++failures;
                        System.out.println("Result don't match. Input = " + input + ", system hash = " + Sha256.bytesToHex(systemHash) + ", personal hash = " + Sha256.bytesToHex(personalHash));
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        printDate();
        DecimalFormat format = new DecimalFormat("#.####");
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " (" + format.format((1. * failures / testCount)) + "%) failed.");
        System.out.println(DELIMITER);
    }

    private static void signRabinTest() {
        System.out.println(DELIMITER);
        printDate();
        System.out.println("Test for Rabin sign was started.\n");

        SecureRandom random = new SecureRandom();
        long
                testCount = 0,
                failures = 0;

        for (int i = 100; i <= 1000; i += 100) {
            System.out.println(i);
            for (int j = 0; j < 2; ++j) {
                RabinClient client = new RabinClient(i, random);
                BigInteger message = generateMessage(client.getPublicKey(), i, random);

                try {
                    ++testCount;
                    Object[] sign = client.sign(message, i + random.nextInt(250), random);
                    client.checkSign(sign, client);
                } catch (MessageException e) {
                    ++failures;
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        printDate();
        DecimalFormat format = new DecimalFormat("#.####");
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " (" + format.format((1. * failures / testCount)) + "%) failed.");
        System.out.println(DELIMITER);
    }

    //Test for Blind Sign
    private static void blindSignTest() {
        printDate();
        System.out.println("Test for blind sign was started.");
        SecureRandom random = new SecureRandom();
        long
                testCount = 0,
                failures = 0;

        for (int i = 100; i <= 1000; i += 100) {
            System.out.println(i);
            for (int j = 0; j < 2; ++j) {
                ++testCount;
                RabinClient client = new RabinClient(i, random);
                BigInteger message = generateMessage(client.getPublicKey(), i, random);
                BigInteger[] resultToCheck = client.blindSign(message);
                BigInteger blinder;
                do {
                    blinder = new BigInteger(15, random);
                } while (blinder.gcd(client.getPublicKey()).compareTo(BigInteger.ONE) != 0);
                message = message.multiply(blinder);
                message = message.multiply(blinder);
                message = message.mod(client.getPublicKey());
                BigInteger[] blindSign = client.blindSign(message);
                for (int k = 0; k < 4; ++k) {
                    blindSign[k] = blindSign[k].multiply(blinder.modInverse(client.getPublicKey())).mod(client.getPublicKey());
                }

                if (!checkResult(blindSign, resultToCheck)) {
                    ++failures;
                    System.out.println("result:");
                    for (BigInteger k: blindSign) {
                        System.out.println(k.toString(16));
                    }
                    System.out.println("result to check:");
                    for (BigInteger k: resultToCheck) {
                        System.out.println(k.toString(16));
                    }
                }
            }
        }
        printDate();
        DecimalFormat format = new DecimalFormat("#.####");
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " (" + format.format((1. * failures / testCount)) + "%) failed.");
        System.out.println(DELIMITER);
    }

    private static boolean checkResult(BigInteger[] result, BigInteger[] toCheck) {
        for (BigInteger i : result) {
            for (BigInteger j : toCheck) {
                if (i.compareTo(j) == 0)
                    return true;
            }
        }
        return false;
    }


    private static void blindSignRSATest() {

        System.out.println(DELIMITER);
        System.out.println("Test for RSA blind sign was started.\n");

        long start = System.currentTimeMillis();

        RSAClient alice = new RSAClient();
        RSAClient bob = new RSAClient();

        // (N, e ,d),
        KeyPair alicePair = alice.produceKeyPair();

        RSAPrivateCrtKey alicePrivate = (RSAPrivateCrtKey) alicePair.getPrivate();
        RSAPublicKey alicePublic = (RSAPublicKey) alicePair.getPublic();
        BigInteger N = alicePublic.getModulus();

        // mu = H(msg) * r^e mod N
        String publicMessage = "THE MAGIC WORDS ARE SQUEAMISH OSSIFRAGE";
        BigInteger mu = bob.calculateMu(alicePublic, publicMessage);

        // muPrime = mu' = (m1*Q*QInverseModP + m2*P*PInverseModQ) mod N
        BigInteger muPrime = alice.calculateMuPrimeWithChineseRemainderTheorem(mu, alicePrivate, N);

        // sign = mu'*r^-1 mod N
        String sig = bob.signatureCalculation(muPrime, N);
        System.out.println("Signature produced with Blind RSA procedure for message \"" +
                publicMessage +  "\"\nis: \n" + sig);

        // Bob is checking if the signature he got from Alice is valid, that
        // can be easily computed because (m^d)^e modN = m
        bob.verify(sig,alicePublic);

        System.out.println();
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        System.out.println("Program executed in " + elapsedTimeMillis + " milliseconds");
        System.out.println(DELIMITER);
    }


    private static BigInteger generateMessage(BigInteger n, int bitsNumber, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(bitsNumber + 1, random);
        } while (result.compareTo(n) == -1 && result.compareTo(RabinClient.sqrt(n)) != 1);
        return result;
    }

    /**
     * Generates random string with specified length.
     */
    private static String randomString(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    private static void printDate() {
        System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
    }
}
