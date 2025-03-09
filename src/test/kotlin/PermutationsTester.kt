import com.cormontia.Permutations
import org.junit.jupiter.api.Test
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
    //TODO!+ Add tests for `permutationOptimized1`
}