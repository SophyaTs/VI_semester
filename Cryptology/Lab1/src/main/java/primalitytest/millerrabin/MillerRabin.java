package primalitytest.millerrabin;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {

    public static boolean testPrime(BigInteger n, int certainty) {
        if (certainty <= 0) {
            return false;
        }

        if (n.compareTo(BigInteger.ONE) == 0) {
            return false;
        }
        if (n.compareTo(BigInteger.TWO) == 0) {
            return true;
        }

        BigInteger s = BigInteger.ZERO;
        BigInteger d = n.subtract(BigInteger.ONE);
        
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            s = s.add(BigInteger.ONE);
            d = d.divide(BigInteger.TWO);
        }

        BigInteger a, x;
        Random rand = new Random();
        
        for (int i = 0; i < certainty; i++) {
            
        	a = new BigInteger(n.bitLength(), rand);
            a = a.mod(n.subtract(BigInteger.TWO)).add(BigInteger.TWO);
            x = a.modPow(d, n);

            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }
            
            int j = 0;
            
            for (; s.compareTo(BigInteger.valueOf(j)) > 0; j++) {                
            	x = x.modPow(BigInteger.TWO, n);
                
                if (x.equals(BigInteger.ONE)) {
                    return false;
                }
                
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    break;
                }
            }
            if (s.equals(BigInteger.valueOf(j))) {
                return false;
            }

        }
        return true;
    }
}
