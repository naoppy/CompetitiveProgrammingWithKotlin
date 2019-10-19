package algorithms.strings

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class ManacharKtTest {

    @Test
    fun manacharTest() {
        assertIterableEquals(
                listOf(1, 2, 3, 4, 3, 2, 1),
                manachar("ABABABA").toMutableList()
        )
        assertIterableEquals(
                listOf(1, 2, 1, 4, 1, 2, 3, 2, 1),
                manachar("abaaababa").toMutableList()
        )
        assertIterableEquals(
                listOf(1, 1, 1, 1, 1),
                manachar("abcde").toMutableList()
        )
        assertIterableEquals(
                listOf(1),
                manachar("a").toMutableList()
        )
        assertIterableEquals(
                listOf(1, 2, 3, 2, 1),
                manachar("aaaaa").toMutableList()
        )
        assertIterableEquals(
                emptyList<Int>(),
                manachar("").toMutableList()
        )
    }
}