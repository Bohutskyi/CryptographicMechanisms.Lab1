package additional;

import java.math.BigInteger;

public class GCD {

    private BigInteger gcd, firstCoefficient, secondCoefficient;

    public GCD(BigInteger gcd, BigInteger firstCoefficient, BigInteger secondCoefficient) {
        this.gcd = gcd;
        this.firstCoefficient = firstCoefficient;
        this.secondCoefficient = secondCoefficient;
    }

    public GCD(int gcd, int firstCoefficient, int secondCoefficient) {
        this(new BigInteger(Integer.toString(gcd)), new BigInteger(Integer.toString(firstCoefficient)),
                new BigInteger(Integer.toString(secondCoefficient)));
    }

    public GCD() {}

    public static GCD gcd(BigInteger a, BigInteger b) {
        GCD temp = new GCD(a, BigInteger.ONE, BigInteger.ZERO);
        GCD temp2;
        if (b == BigInteger.ZERO) {
            return temp;
        }
        temp2 = gcd(b, a.mod(b));
        temp = new GCD();
        temp.gcd = temp2.gcd;
        temp.firstCoefficient = temp2.secondCoefficient;
        temp.secondCoefficient = temp2.firstCoefficient.subtract(a.divide(b).multiply(temp2.secondCoefficient));
        return temp;
    }

    public static GCD gcd(int a, int b) {
        return gcd(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)));
    }

    public static BigInteger getReverse(int a, int mod) {
        return getReverse(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(mod)));
    }

    public static BigInteger getReverse(BigInteger a, BigInteger mod) {
        GCD temp = GCD.gcd(a, mod);
        if (temp.gcd.equals(BigInteger.ONE)) {
            if (temp.firstCoefficient.compareTo(BigInteger.ZERO) > 0) {
                return temp.firstCoefficient;
            }
            while (temp.firstCoefficient.compareTo(BigInteger.ZERO) < 0) {
                temp.firstCoefficient = temp.firstCoefficient.add(mod);
            }
            return temp.firstCoefficient;
        } else {
            return null;
        }
    }

    public BigInteger getGcd() {
        return gcd;
    }

    public BigInteger getFirstCoefficient() {
        return firstCoefficient;
    }

    public BigInteger getSecondCoefficient() {
        return secondCoefficient;
    }
}
