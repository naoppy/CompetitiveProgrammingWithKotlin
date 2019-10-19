package algorithms.strings

/**
 * 参考
 * https://snuke.hatenablog.com/entry/2014/12/02/235837
 * https://scrapbox.io/pocala-kyopro/Manachar
 */

/**
 * 文字列[s]について、各i番目の文字を中心とする回文の最大半径が配列の\[i]番目となるような配列を返す
 * ここで、半径とは　(回文の全長+1) / 2　です。
 */
fun manachar(s: String): IntArray {
    val radius = IntArray(s.length)
    var i = 0
    var j = 0
    while (i < s.length) {
        while (i - j >= 0 && i + j < s.length && s[i - j] == s[i + j]) j++
        radius[i] = j
        var k = 1
        while (i - k >= 0 && i + k < s.length && k + radius[i - k] < j) {
            radius[i + k] = radius[i - k]
            k++
        }
        i += k
        j -= k
    }
    return radius
}