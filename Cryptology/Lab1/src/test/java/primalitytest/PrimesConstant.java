package primalitytest;

import java.math.BigInteger;
import java.util.Random;

public class PrimesConstant {
    public static final BigInteger[] primes = {
            new BigInteger("2"), new BigInteger("3"), new BigInteger("5"),
            new BigInteger("7"), new BigInteger("11"), new BigInteger("13"),
            new BigInteger("17"), new BigInteger("19"), new BigInteger("23"),
            new BigInteger("53"), new BigInteger("59"), new BigInteger("61"),
            new BigInteger("67"), new BigInteger("71"), new BigInteger("73"),
            new BigInteger("79"), new BigInteger("83"), new BigInteger("89"),
            new BigInteger("547"), new BigInteger("631"), new BigInteger("919"),
            new BigInteger("2269"), new BigInteger("2437"), new BigInteger("2791"),
            new BigInteger("3169"), new BigInteger("3571"), new BigInteger("4219"),
            new BigInteger("4447"), new BigInteger("5167"), new BigInteger("5419"),
            new BigInteger("6211"), new BigInteger("7057"), new BigInteger("7351"),
            new BigInteger("8269"), new BigInteger("9241"), new BigInteger("10267")
    };

    public static final BigInteger[] notPrimes = {
            new BigInteger("1"), new BigInteger("15"), new BigInteger("20"),
            new BigInteger("155"), new BigInteger("189"), new BigInteger("290"),
            new BigInteger("3933"), new BigInteger("6480"), new BigInteger("36780"),
            new BigInteger("479435"), new BigInteger("55710"), new BigInteger("43567258")
    };

    public static final BigInteger[] bigPrimes = generateBigIntegers(true);

    public static final BigInteger[] bigNotPrimes = generateBigIntegers(false);

    private static BigInteger[] generateBigIntegers(Boolean isPrimes) {
        BigInteger[] bigIntegers = new BigInteger[100];

        for (int i = 0; i < 100; i++) {
            if (isPrimes) {
                bigIntegers[i] = new BigInteger(512 + i, 1000, new Random());
            } else {
                bigIntegers[i] = new BigInteger(512 + i, new Random());
            }
        }

        return bigIntegers;
    }

}
