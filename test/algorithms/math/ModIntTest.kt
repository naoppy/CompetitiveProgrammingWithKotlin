package algorithms.math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ModIntTest {
    @Test
    fun `modInt with no arguments is set value=0, mod=1e9+7`() {
        val modint = ModInt()
        assertEquals((1e9 + 7).toLong(), modint.modulus)
        assertEquals(0, modint.value)
    }

    @Test
    fun `modInt must be initialized given value`() {
        val modint = ModInt(150)
        assertEquals(150, modint.value)
        val modint2 = ModInt(189, 90)
        assertEquals(189 % 90, modint2.value)
        assertEquals(90, modint2.modulus)
    }

    @Test
    fun `modInt plus and minus`() {
        val modint = ModInt(70)
        modint += ModInt(1800000)
        modint -= ModInt(5000)
        assertEquals(70 + 1800000 - 5000, modint.value)
        modint -= ModInt(2L * modint.modulus)
        assertEquals(70 + 1800000 - 5000, modint.value)
        modint += ModInt(3L * modint.modulus + 5000)
        assertEquals(70 + 1800000, modint.value)
    }

    @Test
    fun `modInt multiple`() {
        var modint = ModInt()
        modint *= ModInt(500)
        assertEquals(0, modint.value)

        modint = ModInt(3)
        modint *= ModInt(500)
        assertEquals(1500, modint.value)

        modint = ModInt(2)
        modint *= ModInt(modint.modulus - 1)
        assertEquals(modint.modulus - 2, modint.value)
    }

    @Test
    fun `modInt div`() {
        val modint = ModInt(678813585)
        modint /= ModInt(100000)
        assertEquals(123456789, modint.value)

        val mod13 = ModInt(8, 13)
        assertEquals(5, mod13.inverse().value)
    }

    @Test
    fun `other testcases`() {
        val modint = ModInt(2000000020)
        modint -= ModInt(20)
        assertEquals(999999993, modint.value)
    }
}