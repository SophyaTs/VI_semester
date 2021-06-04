package primalitytest.fermat;

import org.junit.Assert;
import org.junit.Test;
import primalitytest.PrimesConstant;

import java.math.BigInteger;

public class FermatPrimalityTestTest {
    private static final int certainty = 100;

    @Test(expected = ArithmeticException.class)
    public void isZeroPrimeShouldThrowArithmeticException() {
        Fermat.testPrime(BigInteger.ZERO, certainty);
    }

    @Test(expected = ArithmeticException.class)
    public void isNegativePrimeShouldThrowArithmeticException() {
        Fermat.testPrime(BigInteger.valueOf(-1000), certainty);
    }

    @Test
    public void isOnePrimeShouldReturnFalse() {
    	Assert.assertFalse(Fermat.testPrime(BigInteger.ONE, certainty));
    }

    @Test
    public void isTwoPrimeShouldReturnTrue() {
    	Assert.assertTrue(Fermat.testPrime(BigInteger.TWO, certainty));
    }

    @Test
    public void negativeCertaintyAlwaysReturnFalse() {
    	Assert.assertFalse(Fermat.testPrime(BigInteger.valueOf(13), -1));
    	Assert.assertFalse(Fermat.testPrime(BigInteger.valueOf(1000), -1));
    }

    @Test
    public void zeroCertaintyAlwaysReturnFalse() {
    	Assert.assertFalse(Fermat.testPrime(BigInteger.valueOf(13), 0));
    	Assert.assertFalse(Fermat.testPrime(BigInteger.valueOf(1000), 0));
    }

    @Test
    public void isProbablyPrimeShouldReturnTrueForSmallPrimes() {
        for (BigInteger prime : PrimesConstant.primes) {
        	Assert.assertTrue(Fermat.testPrime(prime, certainty));
        }
    }

    @Test
    public void isProbablyPrimeShouldReturnFalseForSmallNotPrimes() {
        for (BigInteger notPrime : PrimesConstant.notPrimes) {
        	Assert.assertFalse(Fermat.testPrime(notPrime, certainty));
        }
    }

    @Test
    public void isProbablyPrimeShouldReturnFalseForNotPrimesBiggerThanInt() {
        int numberOfIsPrimeFalseReturns = 0;
        for (BigInteger bigInteger : PrimesConstant.bigNotPrimes) {
            if (!Fermat.testPrime(bigInteger, certainty)) {
                ++numberOfIsPrimeFalseReturns;
            }
        }

        Assert.assertEquals(99, numberOfIsPrimeFalseReturns, 1);
    }

    @Test
    public void isProbablyPrimeShouldReturnTrueForPrimesBiggerThanInt() {
        int numberOfIsPrimeTrueReturns = 0;
        for (BigInteger bigInteger : PrimesConstant.bigPrimes) {
            if (Fermat.testPrime(bigInteger, certainty)) {
                ++numberOfIsPrimeTrueReturns;
            }
        }

        Assert.assertEquals(99, numberOfIsPrimeTrueReturns, 1);
    }
}