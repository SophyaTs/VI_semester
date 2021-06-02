package gcd.extendedeuclidean;

import java.math.BigInteger;

public class ExtendedEuclidean {

    public static ExtendedEuclideanResult compute(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            return new ExtendedEuclideanResult(
                    BigInteger.ZERO, BigInteger.ONE, b
            );
        }


        ExtendedEuclideanResult gcdResult = compute(b.mod(a), a);
        return new ExtendedEuclideanResult(
                gcdResult.BezoutY.subtract(b.divide(a).multiply(gcdResult.BezoutX)), //1 - (x1 * b / a)
                gcdResult.BezoutX,
                gcdResult.gcd
        );
    }
}
