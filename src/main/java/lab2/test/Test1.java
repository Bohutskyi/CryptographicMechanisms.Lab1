package lab2.test;

import lab2.IncorrectInputException;
import lab2.MillerRabinTest;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static lab2.Maurer.MaurerGeneration;

public class Test1 {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public static void main(String[] args) {
        MillerRabinTest();
        try {
            MaurerTest();
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
        }
    }

    //Test for Miller-Rabin algorithm
    private static void MillerRabinTest() {
        printDate();
        System.out.println("Test for Miller-Rabin algorithm was started.");
        Random random = new Random();
        long
                testCount = 0,
                failures = 0;

        for (int i = 100; i <= 2000; i += 100) {
            for (int j = 0; j < 100; ++j) {
                ++testCount;
                BigInteger temp = new BigInteger(i, random);
                boolean firstResult = temp.isProbablePrime(50);
                boolean secondResult = MillerRabinTest.isProbablePrime(temp, 50);
                if (firstResult != secondResult) {
                    ++failures;
                    System.out.println("Result don't match. Number = " + temp.toString(16) +
                            ", BigInteger result = " + firstResult +
                            ", our implementation = " + secondResult);
                }
            }
        }
        printDate();
        DecimalFormat format = new DecimalFormat("#.####");
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " (" + format.format((1. * failures / testCount)) + "%) failed.");
    }

    //Test for Maurer algorithm
    private static void MaurerTest() throws IncorrectInputException {
        printDate();
        System.out.println("Test for Maurer algorithm was started.");
        long
                testCount = 0,
                failures = 0;

        for (int i = 100; i <= 2000; i += 100) {
            for (int j = 0; j < 1; ++j) {
                ++testCount;
                BigInteger temp = MaurerGeneration(i, 50, 20);
                if (!temp.isProbablePrime(50)) {
                    System.out.println("Not probable prime: " + temp.toString(16));
                    ++failures;
                }
            }
        }
        printDate();
        DecimalFormat format = new DecimalFormat("#.####");
        format.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " (" + format.format((1. * failures / testCount)) + "%) failed.");
    }

    private static void printDate() {
        System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
    }

}
