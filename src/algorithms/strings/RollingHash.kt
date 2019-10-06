package algorithms.strings

import kotlin.math.min

/**
 * 参考：https://github.com/tatyam-prime/kyopro_library/blob/master/RollingHash.cpp
 */

private val bases =
        intArrayOf(257, 262, 266, 275, 276, 281, 285, 290, 296, 302, 306,
                310, 311, 313, 323, 333, 344, 345, 350, 357, 367, 370, 373,
                402, 423, 425, 431, 440, 442, 443, 454, 457, 458, 462, 471,
                478, 481, 487, 489, 492, 499, 501, 502, 503, 506, 514, 524,
                532, 535, 541, 550, 552, 557, 559, 562, 563, 567, 570, 571,
                580, 592, 597, 604, 612
        )

private const val mask30 = (1L shl 30) - 1
private const val mask31 = (1L shl 31) - 1

class RollingHash(val s: String) {
    companion object {
        const val modulus = (1L shl 61) - 1
    }

    val size = s.length
    val base: Long = bases.random().toLong()
    private val power = LongArray(size + 1)
    private val hash = LongArray(size + 1)

    /* 構築 O(N) */
    init {
        power[0] = 1
        hash[0] = 0
        for (i in 1..size) {
            power[i] = power[i - 1].timesMod(base).mod
            hash[i] = (hash[i - 1].timesMod(base) + s[i - 1].toLong()).mod
        }
    }

    /**
     * [begin, end)の文字列のハッシュを返す
     * O(1)
     */
    operator fun get(beginInclusive: Int, endExclusive: Int): Long =
            (hash[endExclusive] + modulus - hash[beginInclusive].timesMod(power[endExclusive - beginInclusive])).mod

    /**
     * ハッシュh1で表される文字列s1
     * ハッシュh2で表される文字長h2lenの文字列s2
     * これらを結合したs1+s2のハッシュhを返す
     * O(1)
     */
    fun connect(h1: Long, h2: Long, h2len: Int): Long =
            (h1.timesMod(power[h2len]) + h2).mod

    /**
     * このローリングハッシュが表す文字列の部分文字列[l1, r1)と
     * otherで与えられたローリングハッシュの表す文字列の部分文字列[l2, r2)の
     * Longest Common Prefixを求める
     * 一致していない場合 -1
     * O(log N)
     */
    fun LCP(other: RollingHash, l1: Int, r1: Int, l2: Int, r2: Int): Int {
        val len = min(r1 - l1, r2 - l2)
        var low = -1
        var high = len + 1
        while (high - low > 1) {
            val mid = low + (high - low) / 2
            if (this[l1, l1 + mid] == other[l2, l2 + mid]) low = mid
            else high = mid
        }
        return low
    }

    /**
     * @return x * y と mod ((1<<61)-1))で合同な値
     * Long型の範囲の0以上の値だが、modがとられているわけではない
     * @param other 2^61-1未満の値
     * thisも2^61-1未満である必要がある
     * 返される値はmod 2^61-1で同じであることは保障されているが、modがとられているわけではないことに注意する
     */
    private fun Long.timesMod(other: Long): Long {
        val au = this shr 31 //63-31bit
        val ad = this and mask31 //30-0bit
        val bu = other shr 31 //63-31bit
        val bd = other and mask31 //30-0bit
        val mid = ad * bu + au * bd
        val midu = mid shr 30 // 63-30bit
        val midd = mid and mask30 // 29-0bit
        return au * bu * 2 + midu + (midd shl 31) + ad * bd
    }

    /**
     * mod 2^61-1を計算する
     */
    private val Long.mod: Long
        get() {
            var y = (this and modulus) + (this shr 61)
            if (y > modulus) y -= modulus
            return y
        }
}