package primalitytest.fermat;

import java.math.BigInteger;
import java.util.Random;

public class FermatPrimalityTest {

    public static boolean isProbablyPrime(BigInteger x, int certainty) {
        if (certainty <= 0) {
            return false;
        }

        if (x.compareTo(BigInteger.ONE) == 0) {
            return false;
        }
        if (x.compareTo(BigInteger.TWO) == 0) {
            return true;
        }

        for (int i = 0; i < certainty; i++) {
            BigInteger curr = new BigInteger(x.bitLength(), new Random());
            curr = curr.mod(x.subtract(BigInteger.TWO)).add(BigInteger.TWO);
            if (!curr.gcd(x).equals(BigInteger.ONE)) {
                return false;
            }
            if (!curr.modPow(x.subtract(BigInteger.ONE), x).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }
}
