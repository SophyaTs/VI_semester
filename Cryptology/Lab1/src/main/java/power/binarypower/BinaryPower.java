package power.binarypower;

import java.math.BigInteger;

public class BinaryPower {

    public static BigInteger power(BigInteger a, int power) {
        if (power == -1) {
            throw new UnsupportedOperationException("Negative power unsupported");
        }

        if (power == 0) {
            return BigInteger.ONE;
        }

        if (power % 2 == 1) {
            return power(a, power-1).multiply(a);
        }
        else {
            BigInteger b = power(a, power/2);
            return new BigInteger(String.valueOf(b.multiply(b)));
        }
    }

}
