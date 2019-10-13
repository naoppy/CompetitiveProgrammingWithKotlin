package algorithms.strings

import kotlin.math.max

/**
 * 参考
 * https://ei1333.github.io/luzhiled/snippets/string/z-algorithm.html
 */

/**
 * 与えられた文字列sから構築したZ-Arrayを返す。
 * O(s.length)
 * Z-Array[i] = sとs.substring(i)のLongestCommonPrefix長
 */
fun `Z-algorithm`(s: String): IntArray {
    if (s == "") return IntArray(0)
    val prefix = IntArray(s.length) { 0 }
    var L = 0
    for (i in 1 until s.length) {
        val diff = i - L
        if (i + prefix[diff] < L + prefix[L]) {
            prefix[i] = prefix[diff]
        } else {
            var k = max(0, prefix[L] - diff)
            while (i + k < s.length && s[k] == s[i + k]) k++
            prefix[i] = k
            L = i
        }
    }
    prefix[0] = s.length
    return prefix
}

/**
 * O(t.length + pattern.length)
 * tの中からpattern文字列が出現するインデックスをリストにして返す。
 */
fun `Z-search`(t: String, pattern: String): List<Int> {
    val list = mutableListOf<Int>()
    val s = "$pattern$$t"
    val zArray = `Z-algorithm`(s)
    for (i in t.indices) {
        if (zArray[i + pattern.length + 1] == pattern.length) list.add(i)
    }
    return list
}