import com.cormontia.permutations
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PermutationsFunctionTester {
    @Test
    fun `the set of permutations of an empty list is a list containing an empty list`() {
        val expected = listOf(mutableListOf<Char>())
        val actual = permutations<Char>(listOf())
        assertEquals(expected, actual)
    }

    @Test
    fun `the set of permutations of a singleton list is a list containing a singleton list`() {
        val expected = listOf(mutableListOf('a'))
        val actual = permutations(listOf('a'))
        assertEquals(expected, actual)
    }

    @Test
    fun `the set of permutations of a list of three is a list of six different permutations`() {
        val expected = listOf(
            mutableListOf('a', 'b', 'c'), mutableListOf('a', 'c', 'b'),
            mutableListOf('b', 'a', 'c'), mutableListOf('b', 'c', 'a'),
            mutableListOf('c', 'a', 'b'), mutableListOf('c', 'b', 'a'),
            )
        val actual = permutations(listOf('a', 'b', 'c'))
        // Convert the results to set, to make the assert ignore ordering.
        assertEquals(expected.toSet(), actual.toSet())
    }
}