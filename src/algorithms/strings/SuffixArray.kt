package algorithms.strings

class SuffixArray(val s: String) {
    private val sa: IntArray = SAIS(s)
    val size = s.length

    /**
     * i番目に小さいsuffixの開始インデックスを返す
     */
    operator fun get(index: Int) = sa[index]

    /**
     * 指定された要素以上の値が現れる最初のインデックスを取得する。
     * 指定された要素がprefixであるとは限らない。
     *
     * @return suffixをソートした時の順番
     */
    private fun lowerBound(t: String): Int {
        var low = -1
        var high = size
        while (high - low > 1) {
            val mid = low + (high - low) / 2
            if (s.substring(sa[mid]) < t) low = mid
            else high = mid
        }
        return high
    }

    /**
     * 指定された要素より大きい値が現れる最初のインデックスを取得する。
     *
     * @return suffixをソートした時の順番
     */
    private fun upperBound(t: String): Int {
        var low = -1
        var high = size
        while (high - low > 1) {
            val mid = low + (high - low) / 2
            if (s.substring(sa[mid]) <= t) low = mid
            else high = mid
        }
        return high
    }

    /**
     * 指定された要素以上、指定した要素以下の区間を半開で返す。
     *
     * @return [first, second)となる区間
     */
    private fun getSection(t: String): Pair<Int, Int> {
        return lowerBound(t) to upperBound(t)
    }

    /**
     * 文字列tが文字列sに含まれているか判定する。
     * O(t.length log s.length) = O(m log n)
     */
    operator fun contains(t: String): Boolean {
        val sec = getSection(t)
        return sec.first != sec.second // lowerの時点でprefixが一致していない場合 first==secondになる
    }

    /**
     * 文字列tが出現するsのインデックスのリストを返す
     * O(max(t.length log s.length, s.length)) = O(max(m log n, n))
     */
    fun appearIndexes(t: String): List<Int> {
        val sec = getSection(t)
        return if (sec.first == sec.second) {
            emptyList()
        } else {
            (sec.first until sec.second).map { sa[it] }
        }
    }
}