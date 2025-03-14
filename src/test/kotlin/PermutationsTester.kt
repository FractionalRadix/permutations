import com.cormontia.Permutations
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class PermutationsTester {
    @Test
    fun `the factorial array of 0 elements is an empty array`() {
        val actual = Permutations.factorialArray(0)
        val expected = LongArray(0)
        assertContentEquals(expected, actual)
    }

    @Test
    fun `the factorial array of a single element is an array containing just the value one`() {
        val actual = Permutations.factorialArray(1)
        val expected = LongArray(1) { 1 }
        assertContentEquals(expected, actual)
    }

    @Test
    fun `the factorial array of two elements is an array containing two times the value one`() {
        val actual = Permutations.factorialArray(2)
        val expected = LongArray(2) { 1 }
        assertContentEquals(expected, actual)
    }

    @Test
    fun `the factorial array of six elements is 1,1,2,6,24,120`() {
        val actual = Permutations.factorialArray(6)
        val expected = LongArray(6)
        for (element in listOf(1L,1L,2L,6L,24L,120L).withIndex()) {
            expected[element.index] = element.value
        }
        assertContentEquals(expected, actual)
    }

    @Test
    fun `the only permutation of an empty list is the empty list`() {
        val iterator = Permutations.generator(listOf<Char>()).iterator()
        val actual = iterator.next()
        val expected = listOf<Char>()
        assertContentEquals(expected, actual)
        assertFalse { iterator.hasNext() }
    }

    @Test
    fun `the only permutation of a singleton list is the singleton list`() {
        val iterator = Permutations.generator(listOf('a')).iterator()
        val actual = iterator.next()
        val expected = listOf('a')
        assertContentEquals(expected, actual)
        assertFalse { iterator.hasNext() }
    }

    @Test
    fun `when given a list of 3 elements, the generator returns all 6 permutations and nothing more`() {
        val iterator = Permutations.generator(listOf('a', 'b', 'c')).iterator()
        val actual = mutableSetOf<List<Char>>()
        for (permutation in iterator) {
            actual.add(permutation)
        }
        val expected = mutableSetOf(
            listOf('a', 'b', 'c'), listOf('a', 'c', 'b'),
            listOf('b', 'a', 'c'), listOf('b', 'c', 'a'),
            listOf('c', 'a', 'b'), listOf('c', 'b', 'a')
        )
        assertEquals(expected, actual)
    }

    //TODO?+ Add tests for `permutation` (won't be necessary if we remove it...)

    @Test
    fun `the first permutation of a list is the list itself`() {
        val expected = listOf(1,2,3,4)
        val actual = Permutations.permutation(listOf(1, 2, 3, 4), 0)
        assertEquals(expected, actual)
    }

    @Test
    fun `the last permutation of a list is the reverse of the list`() {
        val expected = listOf(1,2,3,4)
        val actual = Permutations.permutation(listOf(4, 3, 2, 1), 23)
        assertEquals(expected, actual)
    }

    @Test
    fun `the first and only permutation of an empty list is an empty list`() {
        val expected = listOf<Int>()
        val actual = Permutations.permutation(listOf<Int>(), 0)
        assertEquals(expected, actual)
        assertThrows<IllegalArgumentException> { Permutations.permutation(listOf<Int>(), 1) }
    }

    @Test
    fun `the first and only permutation of a singleton list is a singleton list`() {
        val expected = listOf(7)
        val actual = Permutations.permutation(listOf(7), 0)
        assertEquals(expected, actual)
        assertThrows<IllegalArgumentException> { Permutations.permutation(listOf<Int>(), 1) }
    }

    @Test
    fun `when a permutation is asked that is above the amount of possible permutations then an exception is thrown`() {
        // The last permutation of a list of 4 elements has number 23 (4! - 1, the `- 1` because it's 0-based).
        val actual1 = Permutations.permutation(listOf(1,2,3,4), 23)
        // The previous call should not have caused an exception. The next one should.
        assertThrows<IllegalArgumentException> { Permutations.permutation(listOf(1,2,3,4), 24) }
    }

    @Test
    fun `when a negative permutation is asked then an exception is thrown`() {
        assertThrows<IllegalArgumentException> { Permutations.permutation(listOf(1,2,3,4), -1 ) }
    }



    //TODO!+ Add tests for `permutationOptimized1`
}