package gcd.extendedeuclidean;

import java.math.BigInteger;

public class ExtendedEuclideanResult {
    BigInteger BezoutX;
    BigInteger BezoutY;
    BigInteger gcd;

    public ExtendedEuclideanResult(BigInteger bezoutX, BigInteger bezoutY, BigInteger gcd) {
        BezoutX = bezoutX;
        BezoutY = bezoutY;
        this.gcd = gcd;
    }

    public BigInteger getBezoutX() {
        return BezoutX;
    }

    public BigInteger getBezoutY() {
        return BezoutY;
    }

    public BigInteger getGcd() {
        return gcd;
    }
}
