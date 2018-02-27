package lab1.test;

import lab1.LongNumber;

import org.jscience.mathematics.number.LargeInteger;
import org.openjdk.jmh.runner.RunnerException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

import static lab1.test.BenchmarksLab1.generatePerformanceResults;

public class AppLab1 {

    private static final String DELIMITER = "-------------------------------------------\n";

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        LongNumber first, second;
        if (args.length == 3 && "*".equals(args[1])) {

            first = new LongNumber(args[0]);
            second = new LongNumber(args[2]);

            System.out.println("First input: " + first.hex());
            System.out.println("Second input: " + second.hex() + "\n");
            executeMulOperation(first, second);

        } else if (args.length == 1 && "Statistics".equalsIgnoreCase(args[0])) {
            System.out.println("Statistics will be generated for next inputs: \n"
                    + "First: " + BenchmarksLab1.MyState.first.hex() + "\n"
                    + "Second: " + BenchmarksLab1.MyState.second.hex() + "\n");
            try {
                generatePerformanceResults();
            } catch (RunnerException e) {
                e.printStackTrace();
            }

        } else {
            first = new LongNumber(1024);
            second = new LongNumber(1024);
            first.setRandomValues();
            second.setRandomValues();

            System.out.println("First input: " + first.hex());
            System.out.println("Second input: " + second.hex() + "\n");
            executeMulOperation(first, second);
        }
    }

    private static void executeMulOperation(LongNumber firstInput, LongNumber secondInput) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // Converting randomly generated inputs into different formats for further testing
        BigInteger firstBigInteger = new BigInteger(firstInput.hex(), 16);
        BigInteger secondBigInteger = new BigInteger(secondInput.hex(), 16);
        BigDecimal firstBigDecimal = new BigDecimal(firstBigInteger.toString(10));
        BigDecimal secondBigDecimal = new BigDecimal(secondBigInteger.toString(10));
        LargeInteger firstLargeNumber = LargeInteger.valueOf(firstBigInteger);
        LargeInteger secondLargeNumber = LargeInteger.valueOf(secondBigInteger);

        // LongNumber multiplication
        String customMulResult = LongNumber.LongMul(firstInput, secondInput).hex();
        System.out.println("* Custom Multiplication: \n" + customMulResult);
        System.out.println(DELIMITER);

        String customKaratsubaMulResult = LongNumber.Karatsuba(firstInput, secondInput).hex();
        System.out.println("* Custom Multiplication using Karatsuba algorithm: \n" + customKaratsubaMulResult);
        System.out.println(DELIMITER);

        // BigInteger multiplication
        BigInteger bigIntMulResult = firstBigInteger.multiply(secondBigInteger);
        System.out.println("* BigInteger Multiplication: \n" + bigIntMulResult.toString(16).toUpperCase());
        System.out.println(DELIMITER);

        Method method = BigInteger.class.getDeclaredMethod("multiplyKaratsuba", BigInteger.class, BigInteger.class);
        method.setAccessible(true);
        BigInteger bigIntKaratsubaMulResult = (BigInteger)
                method.invoke(new BigInteger("0"), firstBigInteger, secondBigInteger);
        System.out.println("* BigInteger Multiplication using Karatsuba algorithm: \n" + bigIntKaratsubaMulResult.toString(16).toUpperCase() + "\n");
        System.out.println(DELIMITER);

        method = BigInteger.class.getDeclaredMethod("multiplyToomCook3", BigInteger.class, BigInteger.class);
        method.setAccessible(true);
        BigInteger bigIntToomCook3MulResult = (BigInteger)
                method.invoke(new BigInteger("0"), firstBigInteger, secondBigInteger);
        System.out.println("* BigInteger Multiplication using a 3-way Toom-Cook multiplication algorithm: \n"
                + bigIntToomCook3MulResult.toString(16).toUpperCase() + "\n");
        System.out.println(DELIMITER);

        // BigDecimal multiplication
        BigInteger bigDecMulResult = new BigInteger(firstBigDecimal.multiply(secondBigDecimal).toString(), 10);
        System.out.println("* BigDecimal Multiplication: \n" + bigDecMulResult.toString(16).toUpperCase());
        System.out.println(DELIMITER);

        // LargeNumber multiplication
        BigInteger bigInteger = new BigInteger(firstLargeNumber.times(secondLargeNumber).toText().toString(), 10);
        System.out.println("* LargeInteger Multiplication: \n" + bigInteger.toString(16).toUpperCase());
    }
}
