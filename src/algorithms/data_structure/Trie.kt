package algorithms.data_structure

/**
 * Trie木のノード
 *
 * @param previousNodeID このノードの直前のノードのID
 * @param charSize 追加される文字のあり得る種類の数
 */
class TrieNode(val previousNodeID: Int?, charSize: Int) {
    val next = IntArray(charSize) { -1 }
    /**
     * このノードの子供以下に追加された全ての文字列の個数
     */
    var exist = 0
    /**
     * このノードに完全にマッチする文字列の番号のリスト
     */
    val accepted = ArrayList<Int>()
}

/**
 * Trie木
 *
 * @param charSize 追加される文字のあり得る種類の数
 * @param margin 追加されうる文字のうち、一番小さい文字
 */
class Trie(val charSize: Int = 26, val margin: Int = 'a'.toInt()) {
    private val nodes = ArrayList<TrieNode>().apply {
        add(TrieNode(null, charSize))
    }

    /**
     * 新しいノードを作成し、ノードのidを返す。
     */
    private fun createNode(prevNodeID: Int): Int {
        nodes.add(TrieNode(prevNodeID, charSize))
        return nodes.lastIndex
    }

    /**
     * 文字列[s]をTrie木に追加する。
     *
     * @param s 追加する文字列
     * @param lookingIndex sの何文字目以降を追加するか
     * @param nodeIndex 見ているノードのid
     * @param id 追加する文字列のid
     */
    tailrec fun add(s: String, lookingIndex: Int = 0, nodeIndex: Int = 0, id: Int = nodes[0].exist) {
        if (lookingIndex == s.length) {
            nodes[nodeIndex].accepted.add(id)
        } else {
            val c = s[lookingIndex].toIndex()
            if (nodes[nodeIndex].next[c] == -1) {
                nodes[nodeIndex].next[c] = createNode(nodeIndex)
            }
            nodes[nodeIndex].exist++
            add(s, lookingIndex + 1, nodes[nodeIndex].next[c], id)
        }
    }

    /**
     * 辞書順で[k]番目に小さい文字列を検索する
     *
     * @throws IllegalArgumentException kが[1..count()]の範囲外のとき
     *
     * @param nodeIndex 検索を開始するノードの番号
     */
    tailrec fun searchKthNode(k: Int, nodeIndex: Int = 0): Int {
        require(k < 1 || k > count()) { "$k is out of [1..${count()}]" }

        var newK: Int
        nodes[nodeIndex].accepted.let {
            if (it.size >= k) {
                return@searchKthNode it[k]
            }
            newK = k - it.size
        }

        for (nextNodeIndex in nodes[nodeIndex].next) {
            if (nextNodeIndex == -1) continue
            val children = nodes[nextNodeIndex].exist + nodes[nextNodeIndex].accepted.size

            if (children >= newK) {
                return searchKthNode(newK, nextNodeIndex)
            } else {
                newK -= children
            }
        }

        throw IllegalStateException("unreachable code")
    }

    /**
     * 辞書順で一番小さいノードを検索する
     *
     * @see searchKthNode
     */
    fun minNode(): Int = searchKthNode(1)

    /**
     * 辞書順で一番大きいノードを検索する
     *
     * @see searchKthNode
     */
    fun maxNode(): Int = searchKthNode(count())

    /**
     * 文字列[s]がTrie木に追加されているか判定する
     */
    operator fun contains(s: String): Boolean {
        tailrec fun contains(s: String, lookingIndex: Int, nodeIndex: Int): Boolean {
            if (lookingIndex == s.length) {
                return nodes[nodeIndex].accepted.size > 0
            } else {
                val c = s[lookingIndex].toIndex()
                if (nodes[nodeIndex].next[c] == -1) {
                    return false
                }
                return contains(s, lookingIndex + 1, nodes[nodeIndex].next[c])
            }
        }
        return contains(s, 0, 0)
    }

    /**
     * 文字列[s]を検索しながら、前方部分一致した全ての文字列のidに対して、関数を実行する
     *
     * @param s 検索する文字列
     * @param f 文字列のindexを受け取って処理する関数
     * @param lookingIndex sの何文字目以降を見るか
     * @param nodeIndex 見ているノードのid
     */
    tailrec fun prefixSearchRunFunc(s: String, f: (Int) -> Unit, lookingIndex: Int = 0, nodeIndex: Int = 0) {
        for (i in nodes[nodeIndex].accepted) {
            f(i)
        }
        if (lookingIndex == s.length) {
            return
        } else {
            val c = s[lookingIndex].toIndex()
            if (nodes[nodeIndex].next[c] == -1) return
            prefixSearchRunFunc(s, f, lookingIndex + 1, nodes[nodeIndex].next[c])
        }
    }

    /**
     * 文字列[s]に前方一致した全ての文字列のidのリストを返す
     *
     * @param s 検索する文字列
     * @param lookingIndex sの何文字目以降を見るか
     * @param nodeIndex 見ているノードのid
     */
    tailrec fun prefixSearchToList(s: String, lookingIndex: Int = 0, nodeIndex: Int = 0, list: MutableList<Int> = mutableListOf()): List<Int> {
        list.addAll(nodes[nodeIndex].accepted)
        if (lookingIndex == s.length) {
            return list
        } else {
            val c = s[lookingIndex].toIndex()
            if (nodes[nodeIndex].next[c] == -1) return list
            return prefixSearchToList(s, lookingIndex + 1, nodes[nodeIndex].next[c], list)
        }
    }

    /**
     * 文字列[s]に前方一致した全ての文字列のidを返すシーケンスを返す
     *
     * 全てのidを処理する場合、[prefixSearchToList]を代わりに使用してください。
     * 遅延評価が効果的な文脈でのみ、この関数はパフォーマンスで有利です。
     *
     * @param s 検索する文字列
     * @param lookingIndex sの何文字目以降を見るか
     * @param nodeIndex 見ているノードのid
     */
    fun prefixSearchToSequence(s: String, lookingIndex: Int = 0, nodeIndex: Int = 0): Sequence<Int> = sequence {
        yieldAll(nodes[nodeIndex].accepted)
        if (lookingIndex == s.length) {
            return@sequence
        } else {
            val c = s[lookingIndex].toIndex()
            if (nodes[nodeIndex].next[c] == -1) return@sequence
            yieldAll(prefixSearchToSequence(s, lookingIndex + 1, nodes[nodeIndex].next[c]))
        }
    }

    /**
     * ノードの総数を返す
     */
    fun size(): Int = nodes.size

    /**
     * Trie木に追加された全ての文字列の個数を返す
     */
    fun count(): Int = nodes[0].exist

    /**
     * 文字を[margin]を考慮してインデックス化する
     */
    private fun Char.toIndex(): Int = (this - margin).toInt()
}