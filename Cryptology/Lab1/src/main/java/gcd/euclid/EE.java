package gcd.euclid;

import java.math.BigInteger;

public class EE {

    public static EEResult compute(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            return new EEResult(
                    BigInteger.ZERO, BigInteger.ONE, b
            );
        }


        EEResult gcdResult = compute(b.mod(a), a);
        return new EEResult(
                gcdResult.coefY.subtract(b.divide(a).multiply(gcdResult.coefX)), //1 - (x1 * b / a)
                gcdResult.coefX,
                gcdResult.gcd
        );
    }
}
