package algorithms.strings

/**
 * 2つの文字列のレーベシュタイン距離を求める。
 * O(s.length t.length)
 */
fun levenshteinDistance(s: String, t: String): Int {
    val n = s.length
    val m = t.length
    val dp = Array(n + 1) {
        IntArray(m + 1)
    }
    for (i in 0 until n + 1) dp[i][0] = i
    for (j in 0 until m + 1) dp[0][j] = j

    for (i in 1 until n + 1) {
        for (j in 1 until m + 1) {
            val cost = if (s[i - 1] == t[j - 1]) 0 else 1
            dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
            )
        }
    }
    return dp[n][m]
}