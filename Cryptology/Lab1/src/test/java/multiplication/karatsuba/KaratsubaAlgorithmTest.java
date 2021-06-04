package multiplication.karatsuba;


import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;
import org.junit.Assert;

public class KaratsubaAlgorithmTest {

    

    @Test
    public void checkCorectness() {
        BigInteger a, b;
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            a = BigInteger.valueOf(Math.abs(rand.nextLong()));
            b = BigInteger.valueOf(Math.abs(rand.nextLong()));

            Assert.assertEquals(a.multiply(b), KaratsubaAlgorithm.actionMultiply(a, b));
        }
    }
    
    @Test
    public void multiplicationOnZero() {
    	Assert.assertEquals(BigInteger.ZERO,
                KaratsubaAlgorithm.actionMultiply(new BigInteger("2034954578765486095"), BigInteger.ZERO));
    }

    @Test
    public void checkSigns() {
    	Assert.assertEquals(new BigInteger("500000000000000"),
                KaratsubaAlgorithm.actionMultiply(new BigInteger("-5"), new BigInteger("-100000000000000")));
    }
    
    @Test
    public void checkSignsAgain() {
    	Assert.assertEquals(new BigInteger("-500000000000000"),
                KaratsubaAlgorithm.actionMultiply(new BigInteger("-5"), new BigInteger("100000000000000")));
    }
}