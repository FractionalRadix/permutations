package com.cormontia

import java.lang.Math.floorDiv
import java.math.BigInteger

class Permutations {

    companion object {

        //TODO?~ Use BigInteger?
        /**
         * Determine the factorial of n.
         * @param n A natural number (0 is allowed).
         * @return The factorial of the input value.
         */
        fun factorial(n: Int): Long = if (n<=1) { 1 } else { n * factorial(n-1) }

        //TODO?~ Use this instead of factorial(Int) -> Long ?
        fun factorial_BigInteger(n: Int): BigInteger = if (n <= 1) {
            BigInteger.ONE
        } else {
            BigInteger.valueOf(n.toLong()) *  factorial_BigInteger( n - 1 )
        }

        /**
         * Generate an array, where every element holds the factorial of its index.
         * In other words, an array where for every i, a[i] = i!
         * For example, if n == 5, the resulting array would be [1,1,2,6,24].
         * @param n Size of the array.
         * @return An array where for every i, a[i] is equal to the factorial of i.
         */
        fun factorialArray(n: Int): LongArray {
            val result = LongArray(n)
            var acc = 1L
            for (i in 0 until n) {
                result[i] = acc
                acc *= (i+1)
            }
            return result
        }

        /**
         * Generate an array, where every element holds the factorial of its index.
         * In other words, an array where for every i, a[i] = i!
         * For example, if n == 5, the resulting array would be [1,1,2,6,24].
         * @param n Size of the array.
         * @return An array where for every i, a[i] is equal to the factorial of i.
         */
        fun factorialArray_BigInteger(n: Int): Array<BigInteger> {
            val result = Array(n) { BigInteger.ONE}
            var acc = BigInteger.ONE
            for (i in 0 until n) {
                result[i] = acc
                acc *= ( BigInteger.valueOf(i.toLong()) + BigInteger.ONE)
            }
            return result
        }

        /**
         * Given a list, generate the permutations of that list.
         * Note that this method returns a Sequence. So the next permutation is generated only when the user requests
         * it, not earlier.
         * @param T Type of the elements of the list.
         * @param l The original list.
         * @return A "lazy" Sequence of all permutations of the original list.
         */
        fun <T> generator(l: List<T>): Sequence<List<T>> = sequence {
            val n = l.size

            if (n == 0) {
                // Special case.
                // The empty list has just one permutation: the empty list.
                yield(l)
            } else {

                // The first element of the permutation is repeated (n-1)! times.
                // Within each of these repetitions, the second one is repeated (n-2)! times.
                // And so on.
                // The array `maxCounts` keeps track of these maxima.
                val maxCounts = factorialArray(n)
                maxCounts.reverse()

                // "curCounts" keeps track of how far along we are.
                // Each element of "curCounts" counts from 0 to the associated "maxCount".
                // In other words, `curCounts[i]` is a counter that resets after it reaches `maxCounts[i]`.
                val curCounts = LongArray(n) { 0 }

                // Finally, `idx` tells us which elements we should take from the original array,
                // to arrive at the current permutation.
                val idx = IntArray(n) { 0 }

                // With these preparations done, we can finally start the loop.
                var running = true
                while (running) {

                    val copy = mutableListOf<T>()
                    copy.addAll(l) //TODO!~  We need to find a smarter way, don't want to copy the list in every call, right...?

                    val permutation = mutableListOf<T>()
                    for (i in 0 until n) {
                        permutation.addLast(copy[idx[i]])
                        copy.removeAt(idx[i])
                    }

                    yield(permutation)

                    for (i in 0 until n) {

                        // Increase the counter for every "digit".
                        curCounts[i]++

                        // If any of the counters reaches its maximum value, reset it to 0.
                        // And increase the "idx" for that counter: it now needs to point at the next index in the list.
                        if (curCounts[i] == maxCounts[i]) {
                            curCounts[i] = 0
                            idx[i]++
                            if (idx[i] == n - i) {
                                // If an idx value reaches its maximum (size of the list minus one), reset it.
                                // Unless it is the very first idx value. If THAT one reaches its maximum, then
                                // we have reached the last permutation and we are done.
                                if (i == 0) {
                                    running = false
                                } else {
                                    idx[i] = 0
                                }
                            }
                        }

                    }
                }
            }
        }

        //TODO?~ Use Long or even BigInteger?
        /**
         * Given a list, return the n-th permutation of that list, 0-based.
         * For example, the ordered permutations of ['a','b','c'] would be:
         * [['a','b','c'], ['a','c','b'], ['b', 'a', 'c'], ['b', 'c', 'a'], ['c', 'a', 'b'], ['c', 'b', 'a']]
         * The 4th permutation would be ['c', 'a', 'b'].
         * @param l The list to permute.
         * @param n Index of the permutation, 0-based. Must be smaller than the factorial of the size of the input list.
         * @return The unique permutation of the list for the given index.
         * @throws IllegalArgumentException If the index of the permutation is larger than the amount of possible permutations.
         */
        fun<T> permutation(l: List<T>, n: Int): List<T> {
            val nrOfPermutations = factorial(l.size)
            if  (n >= nrOfPermutations)
                throw IllegalArgumentException("Index of permutation ($n) should not exceed number of permutations ($nrOfPermutations).")
            if (l.isEmpty() || l.size == 1)
                return l
            val cohortSize = factorial(l.size - 1)
            val cohort = floorDiv(n, cohortSize.toInt()) //TODO!~  Find a floorDiv that accepts Long
            val head = l[cohort]
            val remainingList = l.take(cohort) + l.drop(cohort + 1)
            val remainder = n - cohort * cohortSize
            val tail = permutation(remainingList, remainder.toInt())
            val result = mutableListOf(head)
            result.addAll(tail)
            return result
        }

        /**
         * Given a list, return the n-th permutation of that list, 0-based.
         * For example, the ordered permutations of ['a','b','c'] would be:
         * [['a','b','c'], ['a','c','b'], ['b', 'a', 'c'], ['b', 'c', 'a'], ['c', 'a', 'b'], ['c', 'b', 'a']]
         * The 4th permutation would be ['c', 'a', 'b'].
         * @param l The list to permute.
         * @param n Index of the permutation, 0-based. Must be smaller than the factorial of the size of the input list.
         * @return The unique permutation of the list for the given index.
         * @throws IllegalArgumentException If the index of the permutation is larger than the amount of possible permutations.
         */
        fun<T> permutation_BigInteger(l: List<T>, n: BigInteger): List<T> {
            val nrOfPermutations = factorial_BigInteger(l.size)
            if  (n >= nrOfPermutations)
                throw IllegalArgumentException("Index of permutation ($n) should not exceed number of permutations ($nrOfPermutations).")
            if (l.isEmpty() || l.size == 1)
                return l
            val cohortSize = factorial_BigInteger(l.size - 1)
            val divideAndRemainder = n.divideAndRemainder(cohortSize)
            val cohort = divideAndRemainder[0].toInt() //TODO?~ Check for dropped values? Your list shouldn't have more than Integer.MAX_VALUE elements....
            val head = l[cohort]
            val remainingList = l.take(cohort) + l.drop(cohort + 1)
            val remainder = divideAndRemainder[1].toInt()
            val tail = permutation(remainingList, remainder)
            val result = mutableListOf(head)
            result.addAll(tail)
            return result
        }

        fun<T> permutationOptimized1(l: List<T>, n: Int): List<T> {

            val factorials = factorialArray(l.size)

            fun<T> localPermutation(l: List<T>, n: Long): List<T> {
                if (l.isEmpty() || l.size == 1)
                    return l

                val nrOfPermutations = factorials[l.size]

                if  (n >= nrOfPermutations)
                    throw IllegalArgumentException("Index of permutation ($n) should not exceed number of permutations ($nrOfPermutations).")

                val cohortSize = factorials[l.size - 1]
                val cohort = floorDiv(n, cohortSize).toInt()
                val head = l[cohort]
                val remainingList = l.take(cohort) + l.drop(cohort + 1)
                val remainder = n - cohort * cohortSize
                val tail = localPermutation(remainingList, remainder)
                val result = mutableListOf(head)
                result.addAll(tail)

                return result
            }

            return localPermutation(l, n.toLong())
        }

        fun<T> permutationOptimized1_BigInteger(l: List<T>, n: BigInteger): List<T> {

            val factorials = factorialArray_BigInteger(l.size)

            fun<T> localPermutation(l: List<T>, n: BigInteger): List<T> {
                if (l.isEmpty() || l.size == 1)
                    return l

                val nrOfPermutations = factorials[l.size]

                if  (n >= nrOfPermutations)
                    throw IllegalArgumentException("Index of permutation ($n) should not exceed number of permutations ($nrOfPermutations).")

                val cohortSize = factorials[l.size - 1]
                val divideAndRemainder = n.divideAndRemainder(cohortSize)
                //val cohort = floorDiv(n, cohortSize).toInt()
                val cohort = divideAndRemainder[0].toInt() //TODO?~ Check for data loss? We're not likely to have lists with more than Int.MAX_VALUE elements....
                val head = l[cohort]
                val remainingList = l.take(cohort) + l.drop(cohort + 1)
                //val remainder = n - cohort * cohortSize
                val remainder = divideAndRemainder[1]
                val tail = localPermutation(remainingList, remainder)
                val result = mutableListOf(head)
                result.addAll(tail)

                return result
            }

            return localPermutation(l, n)
        }

    }
}