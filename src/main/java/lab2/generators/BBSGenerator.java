package lab2.generators;

import java.math.BigInteger;
import java.util.Random;

/**
 * 9.1. Generator BBS - Blum Blum Shub.
 *
 * p, q – simple numbers (4k + 3) and n = pq.
 * r0 >= 2. ro is random start value.
 * Output sequence {xi} calculates with next steps:
 * ri = ri-1 ^ 2 mod n,
 * xi = ri mod 2.
 *
 * p = D5BBB96D30086EC484EBA3D7F9CAEB07, q = 425D2B9BFDB25B9CF6C416CC6E37B59C1F.
 */
public class BBSGenerator extends Generator {

    protected static final BigInteger p = new BigInteger("0D5BBB96D30086EC484EBA3D7F9CAEB07", 16);
    protected static final BigInteger q = new BigInteger("425D2B9BFDB25B9CF6C416CC6E37B59C1F", 16);
    protected static final BigInteger n = p.multiply(q);
    protected BigInteger r0 = new BigInteger(Long.toString(Math.abs(new Random().nextLong() + 2)));
    protected int x;

    @Override
    public int getNext() {
        r0 = (r0.multiply(r0)).mod(n);
        x = r0.mod(new BigInteger("2")).intValue();
        return x;
    }

}
