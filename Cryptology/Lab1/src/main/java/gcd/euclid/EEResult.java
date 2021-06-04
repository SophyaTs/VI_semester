package gcd.euclid;

import java.math.BigInteger;

public class EEResult {
    BigInteger coefX;
    BigInteger coefY;
    BigInteger gcd;

    public EEResult(BigInteger bezoutX, BigInteger bezoutY, BigInteger gcd) {
        coefX = bezoutX;
        coefY = bezoutY;
        this.gcd = gcd;
    }

    public BigInteger getBezoutX() {
        return coefX;
    }

    public BigInteger getBezoutY() {
        return coefY;
    }

    public BigInteger getGcd() {
        return gcd;
    }
}
