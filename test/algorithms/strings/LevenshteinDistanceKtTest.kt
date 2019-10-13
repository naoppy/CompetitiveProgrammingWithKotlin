package algorithms.strings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LevenshteinDistanceKtTest {
    @Test
    fun levenshteinDistanceTest() {
        assertEquals(
                1,
                levenshteinDistance("Hello", "Hollo")
        )
        assertEquals(
                2,
                levenshteinDistance("Hello", "Holo")
        )
        assertEquals(
                5,
                levenshteinDistance("Hello", "xxxxx")
        )
        assertEquals(
                3,
                levenshteinDistance("kitten", "sitting")
        )
    }
}