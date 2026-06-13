package com.thealgorithms.maths;

/* 孪生素数判定 */

import com.thealgorithms.maths.Prime.PrimeCheck;

public final class TwinPrime {
    private TwinPrime() {
    }

    /**
     * This method returns twin prime of the integer value passed as argument
     *
     * @param inputNumber Integer value of which twin prime is to be found
     * @return (number + 2) if number and (number + 2) are prime, -1 otherwise
     */
    static int getTwinPrime(int inputNumber) {

        // if inputNumber and (inputNumber + 2) are both prime
        // then return (inputNumber + 2) as a result
        if (PrimeCheck.isPrime(inputNumber) && PrimeCheck.isPrime(inputNumber + 2)) {
            return inputNumber + 2;
        }
        // if any one from inputNumber and (inputNumber + 2) or if both of them are not prime
        // then return -1 as a result
        return -1;
    }
}
