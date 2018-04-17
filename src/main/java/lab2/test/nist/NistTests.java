package lab2.test.nist;

import junit.framework.Assert;
import net.stamfest.randomtests.Suite;
import net.stamfest.randomtests.bits.ArrayBits;
import net.stamfest.randomtests.bits.Bits;
import net.stamfest.randomtests.nist.*;
import org.junit.Test;

import java.io.PrintWriter;
import java.math.BigInteger;

import static lab2.Maurer.MaurerGeneration;

public class NistTests {

    /**
     * As with the Serial test, the focus of this test is the frequency of all
     * possible overlapping m-bit patterns across the entire sequence. The purpose
     * of the test is to compare the frequency of overlapping blocks of two
     * consecutive/adjacent lengths ( m and m+1 ) against the expected result for a
     * random sequence.
     *
     * @author NIST / ported by Peter Stamfest
     */
    @Test
    public void ApproximateEntropyTest() {
        BigInteger generatedValue = MaurerGeneration(100, 50, 20);

    /**
     *
     * @param m The length of each block – in this case, the first block length
     *          used in the test. m+1 is the second block length used. Choose m
     *          such that m is smaller than Math.floor(log_2(n))-5 where n is the
     *          length of the sequence.
     */
        ApproximateEntropy ae = new ApproximateEntropy(2);
        Result[] results = ae.runTest(convertToBits(generatedValue));
        ae.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }


    /**
     *
     * The focus of the test is the proportion of ones within M-bit blocks. The
     * purpose of this test is to determine whether the frequency of ones in an
     * M-bit block is approximately M/2, as would be expected under an assumption of
     * randomness. For block size M =1, this test degenerates to the
     * {@link Frequency} (Monobit) test.
     *
     * @author Peter Stamfest
     */
    @Test
    public void BlockFrequency5Test() {
        BigInteger generatedValue = MaurerGeneration(100, 50, 20);

        /**
         * @param blockLenM The block length to test. This is the parameter M from
         *                  the NIST publication.
         */
        ArrayBits fifty_fifty = convertToBits(generatedValue);
        BlockFrequency f = new BlockFrequency(5);
        Result[] results = f.runTest(fifty_fifty);
        f.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }


    @Test
    public void BlockFrequency10Test() {
        BigInteger generatedValue = MaurerGeneration(100, 50, 20);

        BlockFrequency f = new BlockFrequency(10);
        Result[] results = f.runTest(convertToBits(generatedValue));
        f.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of this test is the maximal excursion (from zero) of the random
     * walk defined by the cumulative sum of adjusted (-1, +1) digits in the
     * sequence. The purpose of the test is to determine whether the cumulative sum
     * of the partial sequences occurring in the tested sequence is too large or too
     * small relative to the expected behaviour of that cumulative sum for random
     * sequences. This cumulative sum may be considered as a random walk. For a
     * random sequence, the excursions of the random walk should be near zero. For
     * certain types of non-random sequences, the excursions of this random walk
     * from zero will be large.
     *
     * @author NIST / ported by Peter Stamfest
     */
    @Test
    public void CumulativeSums() {
        BigInteger generatedValue = MaurerGeneration(100, 50, 20);

        CumulativeSums cs = new CumulativeSums();
        Result[] results = cs.runTest(convertToBits(generatedValue));
        cs.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of this test is the peak heights in the Discrete Fourier Transform
     * of the sequence. The purpose of this test is to detect periodic features
     * (i.e., repetitive patterns that are near each other) in the tested sequence
     * that would indicate a deviation from the assumption of randomness. The
     * intention is to detect whether the number of peaks exceeding the 95 %
     * threshold is significantly different than 5 %.
     *
     * @author Peter Stamfest
     */
    @Test
    public void DiscreteFourierTransformTest() {
        BigInteger generatedValue = MaurerGeneration(100, 50, 20);

        DiscreteFourierTransform dft = new DiscreteFourierTransform();
        Result[] results = dft.runTest(convertToBits(generatedValue));
        dft.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of the test is the proportion of zeroes and ones for the entire
     * sequence. The purpose of this test is to determine whether the number of ones
     * and zeros in a sequence are approximately the same as would be expected for a
     * truly random sequence. The test assesses the closeness of the fraction of
     * ones to ½ , that is, the number of ones and zeroes in a sequence should be
     * about the same. All subsequent tests depend on the passing of this test.
     *
     * @author NIST / ported by Peter Stamfest
     */
    @Test
    public void FrequencyTest() {
        BigInteger generatedValue = MaurerGeneration(100, 50, 20);

        Frequency f = new Frequency();
        Result[] results = f.runTest(convertToBits(generatedValue));
        f.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of this test is the length of a linear feedback shift register
     * (LFSR). The purpose of this test is to determine whether or not the sequence
     * is complex enough to be considered random. Random sequences are characterized
     * by longer LFSRs. An LFSR that is too short implies non-randomness.
     *
     * @author NIST / ported by Peter Stamfest
     */
    @Test
    public void LinearComplexityTest() {
        BigInteger generatedValue = MaurerGeneration(1000, 50, 20);

        LinearComplexity lc = new LinearComplexity(500);
        Result[] results = lc.runTest(convertToBits(generatedValue));
        lc.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * Test for the Longest Run of Ones in a Block
     *
     * The focus of the test is the longest run of ones within M-bit blocks. The
     * purpose of this test is to determine whether the length of the longest run of
     * ones within the tested sequence is consistent with the length of the longest
     * run of ones that would be expected in a random sequence. Note that an
     * irregularity in the expected length of the longest run of ones implies that
     * there is also an irregularity in the expected length of the longest run of
     * zeroes. Therefore, only a test for ones is necessary. See Section 4.4.
     *
     * M  The length of each block. The test code has been pre-set to accommodate three values for
     * M: M = 8, M = 128 and M = 10^4 in accordance with the following values of sequence
     * length, n:
     *
     * Minimum n  | M
     * 128        | 8
     * 6272       | 128
     * 750,000    | 10^4
     *
     * N The number of blocks; selected in accordance with the value of M.
     */
    @Test
    public void LongestRunOfOnesTest() {
        BigInteger generatedValue = MaurerGeneration(256, 50, 20);

        LongestRunOfOnes l = new LongestRunOfOnes();
        Result[] results = l.runTest(convertToBits(generatedValue));
        l.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of this test is the number of occurrences of pre-specified target
     * strings. The purpose of this test is to detect generators that produce too
     * many occurrences of a given non-periodic (aperiodic) pattern. For this test
     * and for the Overlapping Template Matching test of Section 2.8, an m -bit
     * window is used to search for a specific m-bit pattern. If the pattern is not
     * found, the window slides one bit position. If the pattern is found, the
     * window is reset to the bit after the found pattern, and the search resumes.
     *
     * @author Peter Stamfest
     */
    @Test
    public void NonOverlappingTemplateMatchingsTest() {
        BigInteger generatedValue = MaurerGeneration(500, 50, 20);

        NonOverlappingTemplateMatchings n = new NonOverlappingTemplateMatchings(9);
        Result[] results = n.runTest(convertToBits(generatedValue));
        n.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of the test is the rank of disjoint sub-matrices of the entire
     * sequence. The purpose of this test is to check for linear dependence among
     * fixed length substrings of the original sequence. Note that this test also
     * appears in the DIEHARD battery of tests.
     *
     * @author Peter Stamfest
     */
    @Test
    public void RankTest() {
        BigInteger generatedValue = MaurerGeneration(2000, 50, 20);

        Rank r = new Rank();
        Result[] results = r.runTest(convertToBits(generatedValue));
        r.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     * The focus of this test is the total number of runs in the sequence, where a
     * run is an uninterrupted sequence of identical bits. A run of length k
     * consists of exactly k identical bits and is bounded before and after with a
     * bit of the opposite value. The purpose of the runs test is to determine
     * whether the number of runs of ones and zeros of various lengths is as
     * expected for a random sequence. In particular, this test determines whether
     * the oscillation between such zeros and ones is too fast or too slow.
     *
     * @author Peter Stamfest
     */
    @Test
    public void IdenticalBitRunsTest() {
        BigInteger generatedValue = MaurerGeneration(2000, 50, 20);

        Runs r = new Runs();

        Result[] results = r.runTest(convertToBits(generatedValue));
        r.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }

    /**
     *
     * The focus of this test is the frequency of all possible overlapping blockLength -bit
     patterns across the entire sequence. The purpose of this test is to determine
     whether the number of occurrences of the 2 blockLength blockLength -bit overlapping patterns is
     approximately the same as would be expected for a random sequence. Random
     sequences have uniformity; that is, every blockLength -bit pattern has the same chance
     of appearing as every other blockLength -bit pattern. Note that for blockLength = 1, the Serial
     test is equivalent to the Frequency test.
     *
     * @author NIST / ported by Peter Stamfest
     */
    @Test
    public void SerialTest() {
        BigInteger generatedValue = MaurerGeneration(2000, 50, 20);

        Serial s = new Serial(2);
        Result[] results = s.runTest(convertToBits(generatedValue));
        s.report(System.out, results);

        Result s1 = results[0];
        Result s2 = results[1];

        Assert.assertTrue(s1.isPassed());
        Assert.assertTrue(s2.isPassed());
    }

    /**
     * The focus of this test is the number of bits between matching patterns (a
     * measure that is related to the length of a compressed sequence). The purpose
     * of the test is to detect whether or not the sequence can be significantly
     * compressed without loss of information. A significantly compressible sequence
     * is considered to be non-random.
     *
     * @author Peter Stamfest
     */
    @Test
    public void UniversalMaurerTest() {
        BigInteger generatedValue = MaurerGeneration(4000, 50, 20);

        Universal u = new Universal();
        Result[] results = u.runTest(convertToBits(generatedValue));
        u.report(System.out, results);

        Assert.assertTrue(results[0].isPassed());
    }



        private ArrayBits convertToBits(BigInteger generatedValue) {
        return new ArrayBits(generatedValue.toByteArray());
    }
}
