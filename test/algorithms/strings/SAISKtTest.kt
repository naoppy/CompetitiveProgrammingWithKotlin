package algorithms.strings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SAISKtTest {
    private fun List<Int>.toTestString() = this.toIntArray().toTestString()
    private fun IntArray.toTestString() = this.joinToString(",")
    private fun createSuffixArray(s: String) = s.indices.sortedBy { s.substring(it) }
    private fun suffixArrayTest(s: String) {
        val expect = createSuffixArray(s).toTestString()
        val sais = SAIS(s).toTestString()
        assertEquals(expect, sais)
    }

    private fun String.test() = suffixArrayTest(this)

    @Test
    fun SAIS() {
        "mmiissiissiippii".test()
        "ecasdqina".test()
        "abcabcabdacfabc".test()
        "ababababababababab".test()
        "naoppy".test()
        "aaaaaaaaaaaaaaaaaaaa".test()
        "a".test()
        "".test()
    }

    @Test
    fun testCaseCreateTest() {
        assertEquals(listOf(0, 1, 2), createSuffixArray("abc"))
        assertEquals(listOf(0), createSuffixArray("a"))
        assertEquals(listOf<Int>(), createSuffixArray(""))
    }
}