import com.cormontia.MemoizedFactorialBigInteger
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertEquals

class MemoizedFactorialBigIntegerTester {
    @Test
    fun `the factorial of 0 is 1`() {
        val m = MemoizedFactorialBigInteger()
        assertEquals(BigInteger.ONE, m.fac(0))
    }

    @Test
    fun `the factorial of 1 is 1`() {
        val m = MemoizedFactorialBigInteger()
        assertEquals(BigInteger.ONE, m.fac(1))
    }

    @Test
    fun `the factorial of 2 is 2`() {
        val m = MemoizedFactorialBigInteger()
        assertEquals(BigInteger.valueOf(2), m.fac(2))
    }

    @Test
    fun `the factorial of 7 is 5040`() {
        val m = MemoizedFactorialBigInteger()
        assertEquals( BigInteger.valueOf(5040), m.fac(7))
    }

    @Test
    fun `if we determine a factorial first and a higher factorial after that then the memoization does not introduce errors`() {
        val m = MemoizedFactorialBigInteger()
        assertEquals( BigInteger.valueOf(6), m.fac(3))
        assertEquals(BigInteger.valueOf(5040), m.fac(7))
    }

}