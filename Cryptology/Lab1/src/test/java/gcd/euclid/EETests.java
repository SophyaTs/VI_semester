package gcd.euclid;

import org.junit.Test;

import gcd.euclid.EE;
import gcd.euclid.EEResult;

import org.junit.Assert;

import java.math.BigInteger;


public class EETests {
    @Test
    public void numbersWithGCDNotEqualsOne() {
        EEResult result = EE.compute(
                new BigInteger("180234567898765432123456786543245678765432456"),
                new BigInteger("150234567890987654323456783456789034567898765432345678976")
        );

        Assert.assertEquals(BigInteger.valueOf(8), result.getGcd());
        Assert.assertEquals(new BigInteger("1229954313815509585227806282539030464679998834377949985"),
                result.getBezoutX());
        Assert.assertEquals(new BigInteger("-1475561100203085651023995393195990040638327"),
                result.getBezoutY());
    }

    @Test
    public void testCoprime() {
        EEResult result = EE.compute(
                new BigInteger("37"),
                new BigInteger("19")
        );
        
        
        
        Assert.assertEquals(BigInteger.ONE, result.getGcd());
        Assert.assertEquals(new BigInteger("-63524105172722478387163175082519043969162660373251473231"),
                result.getBezoutX());
        Assert.assertEquals(new BigInteger("762090896102493372855587198992341642241962142"),
                result.getBezoutY());
    }
}