package algorithms.strings

/**
 * 参考:https://mametter.hatenablog.com/entry/20180130/p1
 * https://shogo82148.github.io/homepage/memo/algorithm/suffix-array/sa-is.html
 * https://qiita.com/flare/items/ac11972dbc590a91980d
 * https://echizen-tm.hatenadiary.org/entry/20110728/1311871765
 * https://qiita.com/flare/items/20439a1db54b367eea70
 * https://speakerdeck.com/flare/sa-is
 */

private typealias TypeArray = BooleanArray

private const val S = true
private const val L = false
private const val K = 1 shl 8 // 文字列の種類

/**
 * インデックスがLeft-Most S-typeか判定する
 *
 * @param index 文字列のインデックス, [0, s.length)の範囲
 * @return LMSならtrue
 */
private fun isLMS(type: TypeArray, index: Int): Boolean =
        index > 0 && type[index - 1] == L && type[index] == S

/**
 * SA-IS法によりSuffixArrayをO(s.length)で構築する
 *
 * @param s 構築する文字列
 * @return suffix array
 */
fun SAIS(s: String): IntArray {
    if (s == "") return IntArray(0)
    val newS = "$s$"
    return SAIS(newS.map { it.toInt() }).copyOfRange(1)
}

private fun IntArray.copyOfRange(fromIndex: Int): IntArray = this.copyOfRange(fromIndex, size)

/**
 * induced sortを実行する
 *
 * @param s 構築元の文字列
 * @param type type[i]はsuffix[i]がS-typeかL-typeかの配列
 * @param lmsIndexes LMSのインデックスのリスト、[0, s.length)の値を取る。
 * ソートされていた場合、正しいSuffixArrayを返す。
 * @return 手順に沿って構築したSuffixArray、間違えている可能性がある
 */
private fun inducedSort(s: List<Int>, type: TypeArray, lmsIndexes: List<Int>): IntArray {
    val suffixArr = IntArray(s.size) { -1 }
    val bin = IntArray(K).apply {
        fill(0)
        s.forEach { this[it + 1]++ }
        for (i in 1 until size) this[i] += this[i - 1]
    }

    // LMSのインデックスをbinの終わりから入れる
    val count = IntArray(K) { 0 }
    lmsIndexes.reversed()
            .map { i -> i to s[i] }
            .forEach { (index, ch) ->
                suffixArr[bin[ch + 1] - 1 - count[ch]] = index
                count[ch]++
            }
    // SAを上から走査して、L-typeを上から詰めていく
    count.fill(0)
    for (i in suffixArr.indices) {
        if (suffixArr[i] == -1) continue
        if (suffixArr[i] == 0) continue
        if (type[suffixArr[i] - 1] == S) continue

        val ch = s[suffixArr[i] - 1] // これはL-typeの文字
        suffixArr[bin[ch] + count[ch]] = suffixArr[i] - 1
        count[ch]++
    }
    //SAを下から走査して、S-typeを下から詰めていく
    count.fill(0)
    for (i in suffixArr.indices.reversed()) {
        if (suffixArr[i] == -1) continue
        if (suffixArr[i] == 0) continue
        if (type[suffixArr[i] - 1] == L) continue

        val ch = s[suffixArr[i] - 1] // これはS-typeの文字
        suffixArr[bin[ch + 1] - 1 - count[ch]] = suffixArr[i] - 1
        count[ch]++
    }

    return suffixArr
}

private fun SAIS(s: List<Int>): IntArray {
    val type = TypeArray(s.size).apply {
        this[lastIndex] = S
        for (i in (lastIndex - 1) downTo 0) {
            this[i] =
                    when {
                        s[i] < s[i + 1] -> S
                        s[i] > s[i + 1] -> L
                        s[i] == s[i + 1] -> this[i + 1]
                        else -> throw IllegalStateException()
                    }
        }
    }
    val lmsIndices = s.indices.filter { isLMS(type, it) }
    val suffixArray = inducedSort(s, type, lmsIndices)
    val inducedSortedLMSIndexes = suffixArray.filter { isLMS(type, it) }

    // map[i] = j は インデックスiから始まるLMS-SubStringはj番目に小さい
    val map = HashMap<Int, Int>()
    var num = 0
    map[inducedSortedLMSIndexes[0]] = num
    inducedSortedLMSIndexes.zipWithNext { i, j ->
        for (d in s.indices) {
            if (s[i + d] != s[j + d] || isLMS(type, i + d) != isLMS(type, j + d)) {
                // LMS部分文字列が異なる
                num++
                break
            } else if (d > 0 && (isLMS(type, i + d) || isLMS(type, j + d))) {
                // LMS-SubStringの終端に到達
                break
            }
        }
        map[j] = num
    }
    val nums = lmsIndices.map { map[it]!! }//文字列のLMS-substringを数字に置き換えたリスト

    return if (nums.size == num + 1) {//LMS-SubStringに重複がない場合
        inducedSort(s, type, inducedSortedLMSIndexes)
    } else {//重複があった
        val sa = SAIS(nums)
        val seed = sa.map { lmsIndices[it] }
        inducedSort(s, type, seed)
    }
}