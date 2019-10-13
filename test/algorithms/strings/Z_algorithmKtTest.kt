package algorithms.strings

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class Z_algorithmKtTest {
    private fun correctZArray(s: String): IntArray {
        return s.indices.map {
            val subS = s.substring(it)
            var match = 0
            while (match < subS.length && s[match] == subS[match]) match++
            match
        }.toIntArray()
    }

    @Test
    fun `Z-algorithmTest`() {
        val s1 = "Hello"
        assertIterableEquals(
                correctZArray(s1).toMutableList(),
                `Z-algorithm`(s1).toMutableList()
        )
        val s2 = "a"
        assertIterableEquals(
                correctZArray(s2).toMutableList(),
                `Z-algorithm`(s2).toMutableList()
        )
        val s3 = ""
        assertIterableEquals(
                correctZArray(s3).toMutableList(),
                `Z-algorithm`(s3).toMutableList()
        )
        val s4 = "aabaaabaaba"
        assertIterableEquals(
                correctZArray(s4).toMutableList(),
                `Z-algorithm`(s4).toMutableList()
        )
    }

    @Test
    fun `Z-searchTest`() {
        assertIterableEquals(
                listOf(0),
                `Z-search`("Hello", "H")
        )
        assertIterableEquals(
                listOf(0),
                `Z-search`("H", "H")
        )
        assertIterableEquals(
                listOf(1, 4),
                `Z-search`("abcabc", "bc")
        )
        assertIterableEquals(
                emptyList<Int>(),
                `Z-search`("", "abc")
        )
        assertIterableEquals(
                listOf(0, 1, 2),
                `Z-search`("abc", "")
        )
        assertIterableEquals(
                listOf(1, 5, 8),
                `Z-search`("aabaaabaaba", "aba")
        )
    }
}