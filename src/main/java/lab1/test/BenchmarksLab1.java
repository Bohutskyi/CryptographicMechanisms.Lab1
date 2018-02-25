package lab1.test;

import lab1.LongNumber;
import lab1.LongNumberPair;
import lab1.Pair;
import lab1.SubtractionException;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class BenchmarksLab1 {

    @State(Scope.Thread)
    public static class MyState {
        public static LongNumber first = new LongNumber("9EC0B05B166283CC23148D42D9914D488E981F6974F6507438054CAF5E53F0B68B04EE5179452F5D3AD5CB22BC2A161DDCA9EB9F1285DC6CA3BAEB3021FF4FE450D25DB84BB738D97F69F34AC86E1ACF2FB21936F835BD9A98F5828BCD82FE5F6CCCD7F3252BEA7FD69A8C6719B07334A83FA643C1C4AAB022BCF84F150D72D0327F3195091948E84845909000EC888778BE0C58117A6CFEA932C3F4212373D12D1E24099511362D349C350AA5B0A9F326FCB06088DE3D817B4F729821B6BAE9EF1BF75374F684B41CC7A8AFAA0E3C4DCCBE7E32B5A4904D984D0011247A5DEB4B8463456526B0D1976B45EE34E1D48738B7FCAFD0857C81D60A84125F8CD7E7CBBA8422A59C2DB7827D0476A657FE836F4720B608B5AC4CC3E34AD85FFC5FF2B3A5267FFF41A9D038BADC9281BAA3302AE84C29835510C5B064F0E737FBB3A2BFE07E4A4FF61B6E5D1FA975E82465EE59394CF36F5019BA20E0124A07707A1A3D587B073A3D4603BDD182D60850ACEA9F13CAE963B6DC96077DC29E581B870D55F09C10843C6E0A6148ED02F3B613386C1831F9366D6C9EA05FAD975621717AC9B55A85C5DD2C5D35803929E216ADE55C005CAF8F5AB012C8495824C85D9F3E852D414658A7B46156DCFB411837270D3316B111F0E5E929B9078D4BDDED1B2C2A84D38CB55A9E754C9E728AD75F168057B7E689");
        public static LongNumber second = new LongNumber("EF267A9F42AE599C6E7C8EF088033E452C096A2DD3064B18BEE901C6A27AB74090534E81C3E3A68345CAF4255AD81CC0872167104BEDABD36FF55A87A8B1ED35B563432163905F898607890E0D9E4930A26408D70D15A14489D466CC7708F94CAAA36AD1A6BEEBAE178C65AE1D95A2CA3AD0752B942B237D27B3726745DD075545913C517C96990563FB11803033E648F40FCE518A65DCACA65A749DC825DCA8CDC321BEB1987445101805AE2875AB24BB4A33FB541B720CF6FCDEF98118B80A78651BEBB6A18574B2E94091BD2ED170AE12D8BC05A3BAFB95D2F1B7917C64EF62D9EF255B6DE4B20B14AE34E5EF824696C27EC8417F35DFEA242EAC67B6A4F4AC6C9B551C64B75208AADB158720966BBBAD8361A492CA90ACAE027AB43C7EC80D516C9D5684C175B2BB3FA7CB80303126FEA4C836F6B29D3FCC2A60417F3D133B7932304BC2CCBCE9D75688756429B89A82E5D631A6F55A53B92B2705F6678221687509D153B9A24C51FAA8C2746518305257B87EB2ADB97F457DC7461249D911B9E65A9BA14A7EAAD2BC5F601C2705C6BE0B5CF9AAAE99F43B2B60C82C29D702B3EA29500C1535B6421C33AC732807131783E530F00F603DBD5545E523685DFAFC13ACEE57910E8A1C9A4BD026CD2F521E864073499BD20124393B57D63B5CD8E5A7B8AB225DD22677BF5E56E4018E7D96");
    }

    @State(Scope.Thread)
    public static class BigIntegerState {
        public static BigInteger first = new BigInteger(MyState.first.hex(), 16);
        public static BigInteger second = new BigInteger(MyState.first.hex(), 16);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber addLongNumbers() {
        return LongNumber.LongAdd(MyState.first, MyState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger addBigIntegers() {
        return BigIntegerState.first.add(BigIntegerState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static Pair subLongNumbers() throws SubtractionException {
        return LongNumber.LongSub(MyState.first, MyState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger subBigIntegers() {
        return BigIntegerState.first.subtract(BigIntegerState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber mulLongNumbers() {
        return LongNumber.LongMul(MyState.first, MyState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber karatsubaMulLongNumbers() {
        return LongNumber.Karatsuba(MyState.first, MyState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger mulBigIntegers() {
        return BigIntegerState.first.subtract(BigIntegerState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumberPair divLongNumbers() throws SubtractionException {
        return LongNumber.LongDivMod(MyState.first, MyState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger divBigIntegers() {
        return BigIntegerState.first.divide(BigIntegerState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static LongNumber gcdLongNumbers() throws SubtractionException {
        return LongNumber.GCD(MyState.first, MyState.second);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public static BigInteger gcdBigIntegers() {
        return BigIntegerState.first.gcd(BigIntegerState.second);
    }

    public static void generatePerformanceResults() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarksLab1.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
