import com.cormontia.MemoizedFactorial
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MemoizedFactorialTester {
    @Test
    fun `the factorial of 0 is 1`() {
        val m = MemoizedFactorial()
        assertEquals(1, m.fac(0))
    }

    @Test
    fun `the factorial of 1 is 1`() {
        val m = MemoizedFactorial()
        assertEquals(1, m.fac(1))
    }

    @Test
    fun `the factorial of 2 is 2`() {
        val m = MemoizedFactorial()
        assertEquals(2, m.fac(2))
    }

    @Test
    fun `the factorial of 7 is 5040`() {
        val m = MemoizedFactorial()
        assertEquals(5040, m.fac(7))
    }

    @Test
    fun `if we determine a factorial first and a higher factorial after that then the memoization does not introduce errors`() {
        val m = MemoizedFactorial()
        assertEquals(6, m.fac(3))
        assertEquals(5040, m.fac(7))
    }
}