package primalitytest.fermat;

import java.math.BigInteger;
import java.util.Random;

public class Fermat {

    public static boolean testPrime(BigInteger n, int certainty) {
        if (certainty <= 0) {
            return false;
        }

        if (n.compareTo(BigInteger.TWO) == 0) {
            return true;
        }
        
        if (n.compareTo(BigInteger.ONE) == 0) {
            return false;
        }
        

        for (int i = 0; i < certainty; i++) {
            BigInteger curr = new BigInteger(n.bitLength(), new Random());
            curr = curr.mod(n.subtract(BigInteger.TWO)).add(BigInteger.TWO);
            
            if (!curr.gcd(n).equals(BigInteger.ONE)) {
                return false;
            }
            
            if (!curr.modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }
}
