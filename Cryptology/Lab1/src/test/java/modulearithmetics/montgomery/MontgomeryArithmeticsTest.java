package modulearithmetics.montgomery;

import power.binarypower.BinaryPower;

import java.math.BigInteger;
import org.junit.Test;
import org.junit.Assert;

public class MontgomeryArithmeticsTest {

    @Test (expected = IllegalArgumentException.class)
    public void evenModuleThrowIllegalArgumentExceptionForMultiplying() {
        MontgomeryArithmetics.modMultiply(BigInteger.ONE, BigInteger.ONE, BigInteger.TWO);
    }

    @Test (expected = IllegalArgumentException.class)
    public void negativeModuleThrowIllegalArgumentExceptionForMultiplying() {
        MontgomeryArithmetics.modMultiply(BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(-1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void zeroModuleThrowIllegalArgumentExceptionForMultiplying() {
        MontgomeryArithmetics.modMultiply(BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
    }

    @Test (expected = IllegalArgumentException.class)
    public void evenModuleThrowIllegalArgumentExceptionForPower() {
        MontgomeryArithmetics.modPower(BigInteger.ONE, BigInteger.ONE, BigInteger.TWO);
    }

    @Test (expected = IllegalArgumentException.class)
    public void negativeModuleThrowIllegalArgumentExceptionForPower() {
        MontgomeryArithmetics.modPower(BigInteger.ONE, BigInteger.ONE, BigInteger.valueOf(-1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void zeroModuleThrowIllegalArgumentExceptionForPower() {
        MontgomeryArithmetics.modPower(BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
    }

    @Test
    public void multiplyingResultShouldBeTheSameAsLib() {
        BigInteger a, b, n;

        a = new BigInteger("23456787654321");
        b = new BigInteger("4567890876543245");
        n = new BigInteger("34567898761");
        Assert.assertEquals(a.multiply(b).mod(n), MontgomeryArithmetics.modMultiply(a,b,n));
    }

    @Test
    public void powerResultShouldBeTheSameAsLib() {
        BigInteger a, b, n;

        a = new BigInteger("23456787654321");
        b = new BigInteger("4567890876543245");
        n = new BigInteger("34567898761");
        Assert.assertEquals(a.modPow(b, n), MontgomeryArithmetics.modPower(a,b,n));
    }
}