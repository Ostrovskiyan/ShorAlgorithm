package ua.ostrovskyi;

import java.math.BigInteger;

public class Main {

    private final static BigInteger one = new BigInteger(Long.toHexString(1), 16);

    public static void main(String[] args) {
        //        final long factorizedNumber = 3559 * 3571;
        final long sourceFactor1 = 99961;
        final long sourceFactor2 = 99971;
        final BigInteger factorizedNumber = BigInteger.valueOf(sourceFactor1).multiply(BigInteger.valueOf(sourceFactor2));
        System.out.println("Factorize " + factorizedNumber);
        System.out.println("Factorized number bit size " + factorizedNumber.bitLength());

        final long[] primes = new long[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};

        int i = 0;
        long startTime = System.currentTimeMillis();
        System.out.println("Basis number - " + primes[i]);
        while (!factorize(primes[i++], factorizedNumber)) {
            System.out.println("Basis number - " + primes[i]);
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("Millis : " + time);
        System.out.println("Second : " + time / 1000.);
        System.out.println("Minutes : " + time / 60_000.);
    }

    private static boolean factorize(long basis, long rawNumberForFactorize) {
        return factorize(basis, BigInteger.valueOf(rawNumberForFactorize));
    }

    private static boolean factorize(long basis, final BigInteger factorizedNumber) {

        BigInteger basisBI = BigInteger.valueOf(basis);

        // Check that basis is factor
        if (!basisBI.gcd(factorizedNumber).equals(one)) {
            System.out.println("Factor 1 - " + basisBI);
            System.out.println("Factor 2 - " + factorizedNumber.divide(basisBI));
            return true;
        }

        for (int possiblePeriod = 1; possiblePeriod < Integer.MAX_VALUE; possiblePeriod++) {
            BigInteger exponent = BigInteger.valueOf(possiblePeriod);
            BigInteger moduleOfPoweredBasis = basisBI.modPow(exponent, factorizedNumber);
            if (!moduleOfPoweredBasis.equals(one)) {
                continue;
            } else {
                if (possiblePeriod % 2 != 0) {
                    return false;
                }

                BigInteger basisInPeriod = basisBI.pow(possiblePeriod / 2);
                BigInteger firstVariableForGcd = basisInPeriod.subtract(one);
                BigInteger secondVariableForGcd = basisInPeriod.add(one);

                if (factorizedNumber.subtract(one).mod(factorizedNumber).equals(basisInPeriod)) {
                    return false;
                }

                System.out.println("==========================ANSWER=============================");
                System.out.println("Period  - " + possiblePeriod);
                BigInteger factor1 = firstVariableForGcd.gcd(factorizedNumber);
                BigInteger factor2 = secondVariableForGcd.gcd(factorizedNumber);
                System.out.println("Factor 1 - " + factor1);
                System.out.println("Factor 2 - " + factor2);
                System.out.println("Checking. Source number  - " + factor1.multiply(factor2));
                System.out.println("=============================================================");
                return true;
            }
        }
        System.out.println("Number cannot be factorized");
        return true;
    }

    private static String toHex(long l) {
        return Long.toHexString(l);
    }

}
