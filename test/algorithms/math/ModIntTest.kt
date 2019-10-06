package algorithms.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ModIntTest {
    @Test
    fun `mod int with no arguments is set mod=1e9+7, value=0`() {
        val modint = ModInt()
        assertEquals(modint.modulus, (1e9 + 7).toLong())
        assertEquals(modint.value, 0)
    }
}