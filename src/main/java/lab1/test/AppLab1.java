package lab1.test;

import lab1.LongNumber;

import lab1.SubtractionException;
import org.openjdk.jmh.runner.RunnerException;
import java.math.BigInteger;
import java.util.Optional;

import static lab1.test.BenchmarksLab1.generatePerformanceResults;

public class AppLab1 {

    private static final String DELIMITER = "-------------------------------------------\n";

    public static void main(String[] args) {

        LongNumber first, second, exp;

        if (args.length == 3) {

            first = new LongNumber(args[0]);
            second = new LongNumber(args[2]);

            System.out.println("First input: " + first.hex());
            System.out.println("Second input: " + second.hex() + "\n");
            executeOperation(args[1], first, second);

        } else if (args.length == 5 && "^".equals(args[1]) && "mod".equalsIgnoreCase(args[3])) {
            first = new LongNumber(args[0]);
            exp = new LongNumber(args[2]);
            second = new LongNumber(args[4]);

            System.out.println("First input: " + first.hex());
            System.out.println("Exponent input: " + exp.hex());
            System.out.println("Second input: " + second.hex() + "\n");
            executePowOperation(first, exp, second);

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
            exp = new LongNumber(20);
            first.setRandomValues();
            second.setRandomValues();
            exp.setRandomValues();

            System.out.println("First input: " + first.hex());
            System.out.println("Exponent input: " + exp.hex());
            System.out.println("Second input: " + second.hex() + "\n");
            executeOperation("+", first, second);
            executeOperation("-", first, second);
            executeOperation("*", first, second);
            executeOperation("**", first, second);
            executeOperation("/", first, second);
            executeOperation("GCD", first, second);
            executePowOperation(first, exp, second);
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
                String customSubResult = null;
                try {
                    customSubResult = Optional.ofNullable(LongNumber.LongSub(firstInput, secondInput))
                            .map(pair -> pair.getLongNumber().hex())
                            .orElse("(Error) Failed on subtracting: first value is less than second one.");
                } catch (SubtractionException ex) {
                    System.out.println(ex.getMessage());
                }
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
            case "**": {
                String customMulResult = LongNumber.Karatsuba(firstInput, secondInput).hex();
                System.out.println("* Custom Multiplication using Karatsuba algorithm: \n" + customMulResult);

                BigInteger mulResult = firstBigInteger.multiply(secondBigInteger);
                System.out.println("* BigInteger Multiplication: \n" + mulResult.toString(16).toUpperCase());
                System.out.println(DELIMITER);
                LongNumber.Karatsuba(BenchmarksLab1.MyState.first, BenchmarksLab1.MyState.second);
                break;
            }
            case "/": {
                String customDivResult = null;
                try {
                    customDivResult = LongNumber.LongDivMod(firstInput, secondInput).getFirst().hex();
                } catch (SubtractionException ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("* Custom Division: \n" + customDivResult);

                String divResult = firstBigInteger.divide(secondBigInteger).toString(16).toUpperCase();
                System.out.println("* BigInteger Division: \n" + divResult);
                System.out.println(DELIMITER);
                break;
            }
            case "GCD": {
                String customGcdResult = null;
                try {
                    customGcdResult = LongNumber.GCD(firstInput, secondInput).hex().toUpperCase();
                } catch (SubtractionException ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("* Custom GCD: \n" + customGcdResult);

                String gcdResult = firstBigInteger.gcd(secondBigInteger).toString(16).toUpperCase();
                System.out.println("* BigInteger GCD: \n" + gcdResult);
                System.out.println(DELIMITER);
                break;
            }
            default:
                break;
        }
    }

        private static void executePowOperation(LongNumber firstInput, LongNumber expInput, LongNumber secondInput){
            BigInteger firstBigInteger = new BigInteger(firstInput.hex(), 16);
            BigInteger secondBigInteger = new BigInteger(secondInput.hex(), 16);
            BigInteger expBigInteger = new BigInteger(expInput.hex(), 16);

            System.out.println("Please wait a couple of minutes, calculating can take some time");
            String customPowResult = null;
            try {
                customPowResult = LongNumber.LongModPowerBarrett(firstInput, expInput, secondInput).hex();
            } catch (SubtractionException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("* Custom Mod Pow using Barrett algorithm: \n" + customPowResult);

            String powResult = firstBigInteger.modPow(expBigInteger, secondBigInteger).toString(16).toUpperCase();
            System.out.println("* BigInteger Mod Pow: \n" + powResult);
            System.out.println(DELIMITER);
        }
}
