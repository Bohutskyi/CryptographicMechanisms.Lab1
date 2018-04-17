package lab3;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Test {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public static void main(String[] args) {
        RabinTest();
    }

    //Test for Rabin system
    private static void RabinTest() {
        printDate();
        System.out.println("Test for Rabin system was started.");
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
    }

    private static BigInteger generateMessage(BigInteger n, int bitsNumber, Random random) {
        BigInteger result;
        do {
            result = new BigInteger(bitsNumber + 1, random);
        } while (result.compareTo(n) == -1 && result.compareTo(RabinClient.sqrt(n)) != 1);
        return result;
    }

    private static void printDate() {
        System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
    }
}
