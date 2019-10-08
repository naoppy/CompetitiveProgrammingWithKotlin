package algorithms.search

/**
 * elementをキーとして、キー以上の要素の中で最も小さい位置のインデックスを返す。
 * 検索範囲は[fromIndex, toIndex)
 *
 * @param fromIndex この位置を含む範囲
 * @param toIndex この位置を含まない範囲
 * @return 0-indexedなインデックス
 */
fun <T : Comparable<T>> List<T>.lowerBound(
        element: T,
        fromIndex: Int = 0,
        toIndex: Int = size
): Int {
    rangeCheck(size, fromIndex, toIndex)

    var low = fromIndex - 1
    var high = toIndex

    while (high - low > 1) {
        val mid = low + (high - low) / 2
        val midVal = get(mid)
        if (midVal < element) low = mid
        else high = mid
    }

    return high
}

/**
 * elementをキーとして、キー以上の要素の中で最も小さい位置のインデックスを返す。
 * 検索範囲は[fromIndex, toIndex)
 *
 * @param fromIndex この位置を含む範囲
 * @param toIndex この位置を含まない範囲
 * @return 0-indexedなインデックス
 */
fun <T> List<T>.lowerBound(
        element: T,
        comparator: Comparator<in T>,
        fromIndex: Int = 0,
        toIndex: Int = size
): Int {
    rangeCheck(size, fromIndex, toIndex)

    var low = fromIndex - 1
    var high = toIndex

    while (high - low > 1) {
        val mid = low + (high - low) / 2
        val midVal = get(mid)
        val cmp = comparator.compare(midVal, element)

        if (cmp < 0) low = mid
        else high = mid
    }

    return high
}

/**
 * elementをキーとして、キーより大きい要素の中で最も小さい位置のインデックスを返す。
 * 検索範囲は[fromIndex, toIndex)
 *
 * @param fromIndex この位置を含む範囲
 * @param toIndex この位置を含まない範囲
 * @return 0-indexedなインデックス
 */
fun <T : Comparable<T>> List<T>.upperBound(
        element: T,
        fromIndex: Int = 0,
        toIndex: Int = size
): Int {
    rangeCheck(size, fromIndex, toIndex)

    var low = fromIndex - 1
    var high = toIndex

    while (high - low > 1) {
        val mid = low + (high - low) / 2
        val midVal = get(mid)
        if (midVal <= element) low = mid
        else high = mid
    }

    return high
}

/**
 * elementをキーとして、キーより大きい要素の中で最も小さい位置のインデックスを返す。
 * 検索範囲は[fromIndex, toIndex)
 *
 * @param fromIndex この位置を含む範囲
 * @param toIndex この位置を含まない範囲
 * @return 0-indexedなインデックス
 */
fun <T> List<T>.upperBound(
        element: T,
        comparator: Comparator<in T>,
        fromIndex: Int = 0,
        toIndex: Int = size
): Int {
    rangeCheck(size, fromIndex, toIndex)

    var low = fromIndex - 1
    var high = toIndex

    while (high - low > 1) {
        val mid = low + (high - low) / 2
        val midVal = get(mid)
        val cmp = comparator.compare(midVal, element)

        if (cmp <= 0) low = mid
        else high = mid
    }

    return high
}

/**
 * Checks that `from` and `to` are in
 * the range of [0..size] and throws an appropriate exception, if they aren't.
 */
private fun rangeCheck(size: Int, fromIndex: Int, toIndex: Int) {
    when {
        fromIndex > toIndex -> throw IllegalArgumentException("fromIndex ($fromIndex) is greater than toIndex ($toIndex).")
        fromIndex < 0 -> throw IndexOutOfBoundsException("fromIndex ($fromIndex) is less than zero.")
        toIndex > size -> throw IndexOutOfBoundsException("toIndex ($toIndex) is greater than size ($size).")
    }
}