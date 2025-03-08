package com.cormontia

import java.math.BigInteger

//TODO?~ Use Long or even BigInteger?
/**
 * A tool to calculate factorials, that uses memoization.
 */
class MemoizedFactorial {
    private val calculated = mutableListOf<Int>()

    init {
        calculated.add(1) // fac(0) == 1
    }

    fun fac(n: Int): Int {
        if (n < calculated.size) {
            return calculated[n]
        }
        else {
            var highestFacSoFar = calculated.last()

            for (i in calculated.size .. n) {
                highestFacSoFar *= i
                calculated.addLast(highestFacSoFar)
            }
            return highestFacSoFar
        }
    }
}
