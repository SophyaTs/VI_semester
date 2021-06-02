package multiplication.karatsuba;

import java.math.BigInteger;

public class KaratsubaAlgorithm {
    private final static int MIN_LENGTH_FOR_START = 20;

    
    public static BigInteger multiply(BigInteger a, BigInteger b) {
        int n = Math.max(a.bitLength(), b.bitLength());
        if (n < MIN_LENGTH_FOR_START) return a.multiply(b);
        n = n / 2 + (n % 2);

        BigInteger aRight = a.shiftRight(n);
        BigInteger aLeft = a.subtract(aRight.shiftLeft(n));
        BigInteger bRight = b.shiftRight(n);
        BigInteger bLeft = b.subtract(bRight.shiftLeft(n));

        BigInteger abRights = multiply(aRight, bRight);
        BigInteger abLefts = multiply(aLeft, bLeft);
        BigInteger abSum = multiply(aRight.add(aLeft), bRight.add(bLeft));

        return abRights
                .shiftLeft(n * 2)
                .add(abLefts)
                .add(abSum
                        .subtract(abRights)
                        .subtract(abLefts)
                        .shiftLeft(n));

    }
}
