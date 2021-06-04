package primalitytest.millerrabin;

import org.junit.Assert;
import org.junit.Test;
import primalitytest.PrimesConstant;

import java.math.BigInteger;

public class MillerRabinPrimalityTestTest {

    private static final int certainty = 100;

    @Test(expected = ArithmeticException.class)
    public void isZeroPrimeShouldThrowArithmeticException() {
        MillerRabin.testPrime(BigInteger.ZERO, certainty);
    }

    @Test(expected = ArithmeticException.class)
    public void isNegativePrimeShouldThrowArithmeticException() {
        MillerRabin.testPrime(BigInteger.valueOf(-1000), certainty);
    }

    @Test
    public void isOnePrimeShouldReturnFalse() {
    	Assert.assertFalse(MillerRabin.testPrime(BigInteger.ONE, certainty));
    }

    @Test
    public void isTwoPrimeShouldReturnTrue() {
    	Assert.assertTrue(MillerRabin.testPrime(BigInteger.TWO, certainty));
    }

    @Test
    public void negativeCertaintyAlwaysReturnFalse() {
    	Assert.assertFalse(MillerRabin.testPrime(BigInteger.valueOf(13), -1));
    	Assert.assertFalse(MillerRabin.testPrime(BigInteger.valueOf(1000), -1));
    }

    @Test
    public void zeroCertaintyAlwaysReturnFalse() {
    	Assert.assertFalse(MillerRabin.testPrime(BigInteger.valueOf(13), 0));
    	Assert.assertFalse(MillerRabin.testPrime(BigInteger.valueOf(1000), 0));
    }

    @Test
    public void isProbablyPrimeShouldReturnTrue() {
        for (BigInteger prime : PrimesConstant.primes) {
        	Assert.assertTrue(MillerRabin.testPrime(prime, certainty));
        }
    }

    @Test
    public void isProbablyPrimeShouldReturnFalse() {
        for (BigInteger notPrime : PrimesConstant.notPrimes) {
        	Assert.assertFalse(MillerRabin.testPrime(notPrime, certainty));
        }
    }

    @Test
    public void isProbablyPrimeShouldReturnFalseForNotPrimesBiggerThanInt() {
        int numberOfIsPrimeFalseReturns = 0;
        for (BigInteger bigInteger : PrimesConstant.bigNotPrimes) {
            if (!MillerRabin.testPrime(bigInteger, certainty)) {
                ++numberOfIsPrimeFalseReturns;
            }
        }

        Assert.assertEquals(99, numberOfIsPrimeFalseReturns, 1);
    }

    @Test
    public void isProbablyPrimeShouldReturnTrueForPrimesBiggerThanInt() {
        int numberOfIsPrimeTrueReturns = 0;
        for (BigInteger bigInteger : PrimesConstant.bigPrimes) {
            if (MillerRabin.testPrime(bigInteger, certainty)) {
                ++numberOfIsPrimeTrueReturns;
            }
        }

        Assert.assertEquals(99, numberOfIsPrimeTrueReturns, 1);
    }
}