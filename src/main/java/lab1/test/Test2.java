package lab1.test;

import lab1.LongNumber;
import lab1.SubtractionException;

import java.math.BigInteger;
import java.util.Random;

public class Test2 {

    public static void main(String[] args) {
//        mulTest(500, 20);
//        GCDTest(200, 20);
//        divisionTest(500, 20);
//        BarretTest(150, 2);
//        mulDigitTest(5000, 20);
//        KaratsubaTest(500, 20);
//        SchonhageStrassenMultiplicationTest(500, 20);
//        subTest(500, 20);
//        addTest(500, 20);
//        cmpTest(500, 20);
//        powerTest(5, 4);
//        specialTest();
    }

    //cmp
    private static void cmpTest(int roundCount, int roundLength) {
        System.out.println("Test for comparison started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            l2 = new LongNumber(i);
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                if (LongNumber.LongCmp(l1, l2) != b1.compareTo(b2)) {
                    failures++;
                    System.out.println("---ERROR---");
                    System.out.println(l1.hex());
                    System.out.println(l2.hex());
                    System.out.println(LongNumber.LongCmp(l1, l2));
                    System.out.println(b1.compareTo(b2));
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    //add
    private static void addTest(int roundCount, int roundLength) {
        System.out.println("Test for addition started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            l2 = new LongNumber(i);
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                if (!LongNumber.LongAdd(l1, l2).hex().equals(b1.add(b2).toString(16).toUpperCase())) {
                    failures++;
                    System.out.println("---ERROR---");
                    System.out.println(l1.hex());
                    System.out.println(l2.hex());
                    System.out.println(LongNumber.LongAdd(l1, l2).hex());
                    System.out.println(b1.add(b2).toString(16));
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    //sub
    private static void subTest(int roundCount, int roundLength) {
        System.out.println("Test for subtraction started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 50; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                l2 = new LongNumber(i - (i / 2));
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                try {
                    if (!LongNumber.LongSub(l1, l2).getLongNumber().hex().equals(b1.subtract(b2).toString(16).toUpperCase())) {
                        failures++;
                        System.out.println("---ERROR---");
                        System.out.println(l1.hex());
                        System.out.println(l2.hex());
                        System.out.println(LongNumber.LongSub(l1, l2).getLongNumber().hex());
                        System.out.println(b1.add(b2).toString(16));
                    }
                } catch (SubtractionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    //mul on one digit
    private static void mulDigitTest(int roundCount, int roundLength) {
        System.out.println("Test for multiplication on one digit started.");
        LongNumber l1;
        BigInteger b1;
        Random random = new Random();
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            if (i % 500 == 0) {
                System.out.println(i);
            }
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                int temp = Math.abs(random.nextInt(10));
                BigInteger tempInt = b1.multiply(new BigInteger(Integer.toString(temp), 10));
                if (!LongNumber.LongMulOneDigit(l1, temp).hex().equals(tempInt.toString(16).toUpperCase())) {
                    failures++;
                    System.out.println("---ERROR---");
                    System.out.println(l1.hex());
                    System.out.println(temp);
                    System.out.println(LongNumber.LongMulOneDigit(l1, temp).hex());
                    System.out.println(tempInt.toString(16));
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    //mul
    private static void mulTest(int roundCount, int roundLength) {
        System.out.println("Test for multiplication started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            l2 = new LongNumber(i);
            if (i % 50 == 0) {
                System.out.println(i);
            }
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                if (!LongNumber.LongMul(l1, l2).hex().equals(b1.multiply(b2).toString(16).toUpperCase())) {
                    failures++;
                    System.out.println("---ERROR---");
                    System.out.println(l1.hex());
                    System.out.println(l2.hex());
                    System.out.println(LongNumber.LongMul(l1, l2).hex());
                    System.out.println(b1.multiply(b2).toString(16));
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    private static void KaratsubaTest(int roundCount, int roundLength) {
        System.out.println("Test for Karatsuba's multiplication started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            l2 = new LongNumber(i);
            if (i % 50 == 0) {
                System.out.println(i);
            }
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                if (!LongNumber.Karatsuba(l1, l2).hex().equals(b1.multiply(b2).toString(16).toUpperCase())) {
                    failures++;
                    System.out.println("---ERROR---");
                    System.out.println(l1.hex());
                    System.out.println(l2.hex());
                    System.out.println(LongNumber.Karatsuba(l1, l2).hex());
                    System.out.println(b1.multiply(b2).toString(16));
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    private static void SchonhageStrassenMultiplicationTest(int roundCount, int roundLength) {
        System.out.println("Test for Schonhage-Strassen's multiplication started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            if (i % 50 == 0) {
                System.out.println(i);
            }
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1 = new LongNumber(i);
                l2 = new LongNumber(i);
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                if (!LongNumber.SchonhageStrassenMul(l1, l2).hex().equals(b1.multiply(b2).toString(16).toUpperCase())) {
                    failures++;
                    System.out.println("---ERROR---");
                    System.out.println(l1.hex());
                    System.out.println(l2.hex());
                    System.out.println(LongNumber.SchonhageStrassenMul(l1, l2).hex());
                    System.out.println(b1.multiply(b2).toString(16));
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    //div
    private static void divisionTest(int roundCount, int roundLength) {
        System.out.println("Test for division started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1 = new LongNumber(i);
                l2 = new LongNumber(i);
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                try {
                    if (!LongNumber.LongDivMod(l1, l2).getFirst().hex().equals(b1.divide(b2).toString(16).toUpperCase())) {
                        System.out.println("---ERROR---");
                        System.out.println(l1.hex());
                        System.out.println(l2.hex());
                        System.out.println(LongNumber.LongDivMod(l1, l2).getFirst().hex());
                        System.out.println(b1.divide(b2).toString(16));
                    }
                } catch (SubtractionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    //mod
    private static void BarretTest(int roundCount, int roundLength) {
        System.out.println("Test for Barret started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 10; i < roundCount; ++i) {
//            if (i % 10 == 0)
            System.out.println(i);
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1 = new LongNumber(2 * i);
                l2 = new LongNumber(i);
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                try {
                    if (!LongNumber.BarrettReduction(l1, l2, LongNumber.getM(l2)).hex().equals(b1.mod(b2).toString(16).toUpperCase())) {
                        failures++;
                        System.out.println("---ERROR---");
                        System.out.println(l1.hex());
                        System.out.println(l2.hex());
                        System.out.println(LongNumber.BarrettReduction(l1, l2, LongNumber.getM(l2)).hex());
                        System.out.println(b1.mod(b2).toString(16));
                    }
                } catch (SubtractionException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    private static void powerTest(int roundCount, int roundLength) {
        System.out.println("Test for power started.");
        LongNumber l1, l2;
        BigInteger b1;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            l2 = new LongNumber(i);
            for (int j = 0; j < roundLength; ++j) {
                System.out.println(j);
                testCount++;
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                    if (!LongNumber.LongPower1(l1, l2).hex().equals(b1.pow(Integer.parseInt(l2.hex(), 16)).toString(16).toUpperCase())) {
                        failures++;
                        System.out.println("---ERROR---");
                        System.out.println(l1.hex());
                        System.out.println(l2.hex());
                        System.out.println(LongNumber.LongPower1(l1, l2).hex());
                        System.out.println(b1.pow(Integer.parseInt(l2.hex(), 16)).toString(16));
                    }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    private static void GCDTest(int roundCount, int roundLength) {
        System.out.println("Test for GCD started.");
        LongNumber l1, l2;
        BigInteger b1, b2;
        long
                testCount = 0,
                failures = 0;
        for (int i = 1; i < roundCount; ++i) {
            l1 = new LongNumber(i);
            l2 = new LongNumber(i);
            System.out.println(i);
            for (int j = 0; j < roundLength; ++j) {
                testCount++;
                l1.setRandomValues();
                l2.setRandomValues();
                b1 = new BigInteger(l1.hex(), 16);
                b2 = new BigInteger(l2.hex(), 16);
                try {
                    if (!LongNumber.GCD(l1, l2).hex().equals(b1.gcd(b2).toString(16).toUpperCase())) {
                        failures++;
                        System.out.println("---ERROR---");
                        System.out.println(l1.hex());
                        System.out.println(l2.hex());
                        System.out.println(LongNumber.GCD(l1, l2).hex());
                        System.out.println(b1.gcd(b2).toString(16));
                    }
                } catch (SubtractionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("Test completed:\n" + testCount + " tests, " + failures + " failed.");
    }

    private static void specialTest() {
        LongNumber l1 = new LongNumber(20);
        l1.setRandomValues();
        LongNumber l2 = new LongNumber("0");
        System.out.println(l2.getSize());
        System.out.println(l1.hex());
        System.out.println(LongNumber.LongMul(l1, l2).hex());
        System.out.println(LongNumber.LongMul(l2, l1).hex());
        System.out.println("---1---");
        System.out.println(LongNumber.Karatsuba(l1, l2).hex());
        System.out.println(LongNumber.Karatsuba(l2, l1).hex());
        System.out.println("---2---");
        System.out.println(LongNumber.SchonhageStrassenMul(l1, l2).hex());
        System.out.println(LongNumber.SchonhageStrassenMul(l2, l1).hex());
        System.out.println("---3---");
        System.out.println(LongNumber.LongPower1(l1, l2).hex());
        System.out.println(LongNumber.LongPower1(l2, l1).hex());
        System.out.println("---4---");
        System.out.println(LongNumber.LongAdd(l1, l2).hex());
        System.out.println(LongNumber.LongAdd(l2, l1).hex());
        System.out.println(LongNumber.LongAdd(l2, l2).hex());
        try {
            System.out.println(LongNumber.LongSub(l1, l2).getLongNumber().hex());
        } catch (SubtractionException e) {
            System.out.println(e.getMessage());
        }
        l2 = new LongNumber("1");
        System.out.println("---5---");
        System.out.println(LongNumber.LongMul(l1, l2).hex());
        System.out.println(LongNumber.LongMul(l2, l1).hex());
        System.out.println("---6---");
        System.out.println(LongNumber.Karatsuba(l1, l2).hex());
        System.out.println(LongNumber.Karatsuba(l2, l1).hex());
        System.out.println("---7---");
        System.out.println(LongNumber.SchonhageStrassenMul(l1, l2).hex());
        System.out.println(LongNumber.SchonhageStrassenMul(l2, l1).hex());
        System.out.println("---8---");
        System.out.println(LongNumber.LongPower1(l1, l2).hex());
        System.out.println(LongNumber.LongPower1(l2, l1).hex());


    }
}
