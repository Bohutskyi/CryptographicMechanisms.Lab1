package test.lab1;

import lab1.LongNumber;

import org.openjdk.jmh.runner.RunnerException;
import java.math.BigInteger;
import java.util.Optional;

import static test.lab1.BenchmarksLab1.generatePerformanceResults;

public class AppLab1 {

    private static final String DELIMITER = "-------------------------------------------\n";

    public static void main(String[] args) {

        LongNumber first, second;

        if (args.length == 3) {

            first = new LongNumber(args[0]);
            second = new LongNumber(args[2]);

            System.out.println("\tFirst input: " + first.hex());
            System.out.println("\tSecond input: " + second.hex());
            executeOperation(args[1], first, second);

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
            first = new LongNumber(1000);
            second = new LongNumber(1000);
            first.setRandomValues();
            second.setRandomValues();

            System.out.println("\tFirst input: " + first.hex());
            System.out.println("\tSecond input: " + second.hex());
            executeOperation("+", first, second);
            executeOperation("-", first, second);
            executeOperation("*", first, second);
            executeOperation("/", first, second);
        }
    }

    private static void executeOperation(String operation,
                                         LongNumber firstInput, LongNumber secondInput) {
        BigInteger firstBigInteger = new BigInteger(firstInput.hex(), 16);
        BigInteger secondBigInteger = new BigInteger(secondInput.hex(), 16);

        switch (operation) {
            case "+": {
                LongNumber customAddResult = LongNumber.LongAdd(firstInput, secondInput);
                System.out.println("* Custom Addition: \n" + customAddResult.hex());

                BigInteger addResult = firstBigInteger.add(secondBigInteger);
                System.out.println("* BigInteger Addition: \n" + addResult.toString(16).toUpperCase());
                System.out.println(DELIMITER);
                break;
            }
            case "-": {
                String customSubResult = Optional.ofNullable(LongNumber.LongSub(firstInput, secondInput))
                        .map(pair -> pair.getLongNumber().hex())
                        .orElse("(Error) Failed on subtracting: first value is less than second one.");
                System.out.println("* Custom Subtracting: \n" + customSubResult);

                BigInteger subResult = firstBigInteger.subtract(secondBigInteger);
                System.out.println("* BigInteger Subtracting: \n" + subResult.toString(16).toUpperCase());
                System.out.println(DELIMITER);
                break;
            }
            case "*": {
                String customMulResult = LongNumber.LongMul(firstInput, secondInput).hex();
                System.out.println("* Custom Multiplication: \n" + customMulResult);

                BigInteger mulResult = firstBigInteger.multiply(secondBigInteger);
                System.out.println("* BigInteger Multiplication: \n" + mulResult.toString(16).toUpperCase());
                System.out.println(DELIMITER);
                break;
            }
            case "/": {
                String customDivResult = LongNumber.LongDivMod(firstInput, secondInput).getFirst().hex();
                System.out.println("* Custom Division: \n" + customDivResult);

                String divResult = firstBigInteger.divide(secondBigInteger).toString(16).toUpperCase();
                System.out.println("* BigInteger Division: \n" + divResult);
                System.out.println(DELIMITER);
                break;
            }
            default: break;
        }
    }
}
