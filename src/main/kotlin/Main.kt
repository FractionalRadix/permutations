package com.cormontia

import java.lang.Math.floorDiv

fun main() {
    println("Hello World!")
    val ps = permutations(listOf('a','b','c'))
    println(ps)
    println()

    for (i in 0..5) {
        val l = permutation(listOf('a', 'b', 'c'), i)
        println("Permutation $i is $l.")
    }

    try {
        val p = permutation(listOf(1, 2, 3), 40)
        println(p)
    } catch (exc: IllegalArgumentException) {
        println("Oops! ${exc.message}")
    }

    println(permutationOptimized1(listOf(1,2,3,4,5), 49))
}

fun<T> prepend(elt: T, l: MutableList<T>): MutableList<T> {
    l.addFirst(elt)
    return l
}

fun<T> permutations(l: List<T>): List<MutableList<T>> {
    val result = mutableListOf(mutableListOf<T>())
    if (l.isEmpty()) {
        return result //TODO?~ Shouldn't the result of permuting an empty list be just an empty list?
    } else {
        result.clear()
        for (i in l.indices) {
            val head = l[i]
            val remainingElements = l.take(i) + l.drop(i + 1)
            val tails = permutations(remainingElements)
            val newLists = tails.map { tail -> prepend(head, tail) }
            result.addAll(newLists)
        }
    }
    return result
}


fun <T> iterativePermutations(l: List<T>): List<List<T>> {
    val result = mutableListOf<List<T>>()
    if (l.isEmpty()) {
        return result
    }

    // Start with an empty list to build permutations
    val stack = mutableListOf<List<T>>()
    stack.add(emptyList())

    for (element in l) {
        val newStack = mutableListOf<List<T>>()
        for (permutation in stack) {
            for (i in 0..permutation.size) {
                // Create a new permutation by inserting the element at position i
                val newPermutation = permutation.toMutableList().apply { add(i, element) }
                newStack.add(newPermutation)
            }
        }
        stack.clear()
        stack.addAll(newStack)
    }

    result.addAll(stack)
    return result
}


fun factorial(n: Int): Int = if (n<=1) { 1 } else { n * factorial(n-1) }


//TODO?~ Use Long or even BigInteger?
// Assumption: n < l.size!
// In words, n is less than the factorial of the size of l.
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
    if (l.isEmpty() || l.size == 1)
        return l
    val nrOfPermutations = factorial(l.size)
    if  (n >= nrOfPermutations)
        throw IllegalArgumentException("Index of permutation ($n) should not exceed number of permutations ($nrOfPermutations).")
    val cohortSize = factorial(l.size - 1)
    val cohort = floorDiv(n, cohortSize)
    val head = l[cohort]
    val remainingList = l.take(cohort) + l.drop(cohort + 1)
    val remainder = n - cohort * cohortSize
    val tail = permutation(remainingList, remainder)
    val result = mutableListOf(head)
    result.addAll(tail)
    return result
}

//TODO?~ Use BigInteger?
fun<T> permutationOptimized1(l: List<T>, n: Int): List<T> {

    fun precalculateFactorials(n: Int): List<Long> {
        val factorials = mutableListOf<Long>()
        factorials.add(1)

        var factorial = 1L
        for (i in 1..n) {
            factorial *= i
            factorials.add(factorial)
        }
        print(factorials)
        return factorials
    }

    val factorials = precalculateFactorials(n)

    fun<T> permutation(l: List<T>, n: Long): List<T> {
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
        val tail = permutation(remainingList, remainder)
        val result = mutableListOf(head)
        result.addAll(tail)
        return result
    }

    return permutation(l, n)
}