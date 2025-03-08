package com.cormontia

import java.math.BigInteger

class MemoizedFactorialBigInteger {
    private val calculated = mutableListOf<BigInteger>()

    init {
        calculated.add(BigInteger.ONE) // fac(0) == 1
    }

    fun fac(n: Int): BigInteger {
        var highestSoFar = calculated.last()

        for (i in calculated.size .. n) {
            val iBig = BigInteger.valueOf(i.toLong())
            //highestSoFar = highestSoFar.times(iBig)
            highestSoFar *= iBig
            calculated.addLast(highestSoFar)
        }
        return highestSoFar
    }
}