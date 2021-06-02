package power.binarypower;

import org.junit.Assert;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

public class BinaryPowerTest {

    @Test
    public void zeroInPowerZeroShouldReturnOne() {
    	Assert.assertEquals(BigInteger.ONE, BinaryPower.power(BigInteger.ZERO, 0));
    }

    @Test
    public void negativeNumberShouldReturnNegativeForOddPower() {
    	Assert.assertEquals(BigInteger.valueOf(-8), BinaryPower.power(BigInteger.valueOf(-2), 3));
    }

    @Test
    public void negativeNumberShouldReturnPositiveForEvenPower() {
    	Assert.assertEquals(BigInteger.valueOf(16), BinaryPower.power(BigInteger.valueOf(-2), 4));
    }

    @Test
    public void resultsForPowerShouldBeSameAsLibPower() {
        BigInteger a;
        int power;
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            a = BigInteger.valueOf(Math.abs(rand.nextInt()));
            power = Math.abs(rand.nextInt() % 1000);
            Assert.assertEquals(a.pow(power), BinaryPower.power(a, power));
        }
    }

}