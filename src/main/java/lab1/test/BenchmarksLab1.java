package lab1.test;

import lab1.LongNumber;
import org.jscience.mathematics.number.LargeInteger;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Generate benchmarks for different custom and Java built-in multiplication methods
 *
 * Developed type
 * @see     LongNumber
 *
 * Built-in types
 * @see     BigInteger
 * @see     BigDecimal
 * @see     LargeInteger
 */
public class BenchmarksLab1 {

    /**
     * Containers with arguments values for testing methods
     */

    @State(Scope.Thread)
    public static class LongNumberState {
        public static LongNumber first768 = new LongNumber(768);
        public static LongNumber first1024 = new LongNumber(1024);
        public static LongNumber second768 = new LongNumber(768);
        public static LongNumber second1024 = new LongNumber(1024);
    }

    @State(Scope.Thread)
    public static class BigIntegerState {
        public static BigInteger first768 = new BigInteger(LongNumberState.first768.hex(), 16);
        public static BigInteger first1024 = new BigInteger(LongNumberState.first1024.hex(), 16);
        public static BigInteger second768 = new BigInteger(LongNumberState.second768.hex(), 16);
        public static BigInteger second1024 = new BigInteger(LongNumberState.second1024.hex(), 16);
    }

    @State(Scope.Thread)
    public static class LargeIntegerState {
        public static LargeInteger first768 = LargeInteger.valueOf(BigIntegerState.first768);
        public static LargeInteger first1024 = LargeInteger.valueOf(BigIntegerState.first1024);
        public static LargeInteger second768 = LargeInteger.valueOf(BigIntegerState.second768);
        public static LargeInteger second1024 = LargeInteger.valueOf(BigIntegerState.second1024);
    }

    @State(Scope.Thread)
    public static class BigDecState {
        public static BigDecimal first768 = new BigDecimal(BigIntegerState.first768);
        public static BigDecimal first1024 = new BigDecimal(BigIntegerState.first1024);
        public static BigDecimal second768 = new BigDecimal(BigIntegerState.second768);
        public static BigDecimal second1024 = new BigDecimal(BigIntegerState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber mulLongNumbers768() {
        return LongNumber.LongMul(LongNumberState.first768, LongNumberState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber mulLongNumbers1024() {
        return LongNumber.LongMul(LongNumberState.first1024, LongNumberState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber karatsubaMulLongNumbers768() {
        return LongNumber.Karatsuba(LongNumberState.first768, LongNumberState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber karatsubaMulLongNumbers1024() {
        return LongNumber.Karatsuba(LongNumberState.first1024, LongNumberState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber strassenMulLongNumbers768() {
        return LongNumber.SchonhageStrassenMul(LongNumberState.first768, LongNumberState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber strassenMulLongNumbers1024() {
        return LongNumber.SchonhageStrassenMul(LongNumberState.first1024, LongNumberState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger mulBigIntegers768() {
        return BigIntegerState.first768.multiply(BigIntegerState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger mulBigIntegers1024() {
        return BigIntegerState.first1024.multiply(BigIntegerState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger karatsubaMulBigIntegers768() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = BigInteger.class.getDeclaredMethod("multiplyKaratsuba", BigInteger.class, BigInteger.class);
        method.setAccessible(true);
        return (BigInteger) method
                .invoke(new BigInteger("0"), BigIntegerState.first768, BigIntegerState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger karatsubaMulBigIntegers1024() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = BigInteger.class.getDeclaredMethod("multiplyKaratsuba", BigInteger.class, BigInteger.class);
        method.setAccessible(true);
        return (BigInteger) method
                .invoke(new BigInteger("0"), BigIntegerState.first1024, BigIntegerState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger toomCookMulBigIntegers768() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = BigInteger.class.getDeclaredMethod("multiplyToomCook3", BigInteger.class, BigInteger.class);
        method.setAccessible(true);
        return (BigInteger) method
                .invoke(new BigInteger("0"), BigIntegerState.first768, BigIntegerState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger toomCookMulBigIntegers1024() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = BigInteger.class.getDeclaredMethod("multiplyToomCook3", BigInteger.class, BigInteger.class);
        method.setAccessible(true);
        return (BigInteger) method
                .invoke(new BigInteger("0"), BigIntegerState.first1024, BigIntegerState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigDecimal mulBigDecimals768() {
        return BigDecState.first768.multiply(BigDecState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static BigDecimal mulBigDecimals1024() {
        return BigDecState.first1024.multiply(BigDecState.second1024);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LargeInteger mulLargeIntegers768() {
        return LargeIntegerState.first768.times(LargeIntegerState.second768);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @BenchmarkMode(Mode.All)
    public static LargeInteger mulLargeIntegers1024() {
        return LargeIntegerState.first1024.times(LargeIntegerState.second1024);
    }


    public static void generatePerformanceResults() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarksLab1.class.getSimpleName())
                .warmupIterations(20)
                .measurementIterations(20)
                .forks(1)
                .build();

        new Runner(opt).run();
    }


}
