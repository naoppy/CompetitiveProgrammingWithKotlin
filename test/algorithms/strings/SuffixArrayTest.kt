package algorithms.strings

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SuffixArrayTest {

    @Test
    fun contains() {
        val sa = SuffixArray("abracadabra")

        assertTrue(sa.contains("abracadabra"))
        assertTrue(sa.contains("c"))
        assertTrue(sa.contains("a"))
        assertTrue(sa.contains("r"))
        assertTrue(sa.contains(""))

        assertFalse(sa.contains("braa"))
    }

    @Test
    fun appearIndexes() {
        val sa = SuffixArray("abracadabra")

        assertIterableEquals(
                listOf(0),
                sa.appearIndexes("abracadabra")
        )
        assertIterableEquals(
                listOf(4),
                sa.appearIndexes("c")
        )
        assertIterableEquals(
                listOf(0, 3, 5, 7, 10),
                sa.appearIndexes("a").sorted()
        )
        assertIterableEquals(
                listOf(2, 9),
                sa.appearIndexes("r").sorted()
        )
        assertIterableEquals(
                "abracadabra".indices.toList().sorted(),
                sa.appearIndexes("").sorted()
        )
        assertIterableEquals(
                listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                sa.appearIndexes("").sorted()
        )

        assertIterableEquals(
                emptyList<Int>(),
                sa.appearIndexes("braa")
        )
        assertIterableEquals(
                emptyList<Int>(),
                sa.appearIndexes("h")
        )
    }
}