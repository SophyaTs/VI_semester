package modarithmetics.montgomeryarithmetics;

import java.math.BigInteger;

import gcd.euclid.EE;
import gcd.euclid.EEResult;

public class MontgomeryArithmetics {

    private static BigInteger redc(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger r = BigInteger.TWO.shiftLeft(n.bitLength());
        EEResult eeResult = EE.compute(n, r);

        BigInteger t = a.multiply(b);
        BigInteger m = t.multiply(eeResult.getBezoutX().negate()).and(r.subtract(BigInteger.ONE));
        BigInteger p = t.add(m.multiply(n)).shiftRight(n.bitLength());

        if (p.compareTo(n) >= 0) {
            return p.subtract(n);
        } else {
            return p;
        }
    }

    public static BigInteger modMultiply(BigInteger a, BigInteger b, BigInteger mod) {
        if (!mod.testBit(0) || mod.compareTo(BigInteger.ONE) <= 0) {
            throw new IllegalArgumentException("Mod should be a positive odd number");
        }
        
        BigInteger uModified = redc(
                a.shiftLeft(mod.bitLength()).mod(mod),
                b.shiftLeft(mod.bitLength()).mod(mod),
                mod
        );
        
        return redc(uModified, BigInteger.ONE, mod);
    }

    public static BigInteger modPower(BigInteger a, BigInteger power, BigInteger mod) {
        if (!mod.testBit(0) || mod.compareTo(BigInteger.ONE) <= 0) {
            throw new IllegalArgumentException("Mod should be a positive odd number");
        }
        
        BigInteger aModified = a.shiftLeft(mod.bitLength()).mod(mod);
        BigInteger xModified = BigInteger.ONE.shiftLeft(mod.bitLength()).mod(mod);
        
        for (int i = power.bitLength() - 1; i >= 0; i--) {
            xModified = redc(xModified, xModified, mod);
            if (power.testBit(i)) {
                xModified = redc(xModified, aModified, mod);
            }
        }

        return redc(xModified, BigInteger.ONE, mod);
    }
}
