package com.cormontia

import java.math.BigInteger

fun main() {

    val ps = permutations(listOf('a','b','c'))
    println(ps)
    println()

    for (i in 0L..5L) {
        val l = Permutations.permutation(listOf('a', 'b', 'c'), BigInteger.valueOf(i))
        println("Permutation $i is $l.")
    }

    try {
        val p = Permutations.permutation(listOf(1, 2, 3),  BigInteger.valueOf(40L))
        println(p)
    } catch (exc: IllegalArgumentException) {
        println("There weren't that many permutations! ${exc.message}")
    }

    println(Permutations.permutationOptimized1(listOf(1,2,3,4,5), BigInteger.valueOf(49L)))

    val iterator = Permutations.generator(listOf("a", "b", "c", "d")).iterator()
    //val iterator = Permutations.generator(listOf("a", "b", "c")).iterator()
    //val iterator = Permutations.generator(listOf("a")).iterator()
    //val iterator = Permutations.generator(listOf<String>()).iterator()
    while (iterator.hasNext()) {
        val perm = iterator.next()
        println("permutation: $perm")
    }
}


//TODO?- We now have the function that generates the permutations lazily.
fun<T> permutations(l: List<T>): List<MutableList<T>> {

    fun<T> prepend(elt: T, l: MutableList<T>): MutableList<T> {
        l.addFirst(elt)
        return l
    }

    val result = mutableListOf(mutableListOf<T>())
    if (l.isEmpty()) {
        return result
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

//TODO?- We now have the function that generates the permutations lazily.
fun <T> iterativePermutations(l: List<T>): List<List<T>> {
    val result = mutableListOf<List<T>>()
    if (l.isEmpty()) {
        return listOf(mutableListOf())
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
