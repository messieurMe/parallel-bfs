package graph

import kotlin.math.absoluteValue

const val CUBIC_SIZE = 499
const val CUBIC_MAX_SIZE = 501
const val CUBIC_MAX_SIZE_DOUBLE = CUBIC_MAX_SIZE * CUBIC_MAX_SIZE

private const val MASK = 511
private const val mask2 = 511 shl 9
private const val mask3 = 511 shl 18

class CubicGraph(private val diameter: Int = 499) : Graph {

    override val size: Int
        get() = 512 * 512 * 512

    override fun neighbourNodes(from: Int): List<Int> {
        val a = from and MASK
        val b = (from shr 9) and MASK
        val c = (from shr 18) and MASK

        val nodes = buildList {
            if (validUIndex(a)) add(toUId(a + 1, b, c))
            if (validBIndex(a)) add(toUId(a - 1, b, c))
            if (validUIndex(b)) add(toUId(a, b + 1, c))
            if (validBIndex(b)) add(toUId(a, b - 1, c))
            if (validUIndex(c)) add(toUId(a, b, c + 1))
            if (validBIndex(c)) add(toUId(a, b, c - 1))
        }
        return nodes
    }

    override fun neighbourNum(from: Int): Int {
        return nexts[from]
     }

    private val nexts = IntArray(size) { from ->
        val a = from and MASK
        val b = (from shr 9) and MASK
          val c = (from shr 18) and MASK

        var counter = 0
        if (validUIndex(a)) counter++
        if (validBIndex(a)) counter++
        if (validUIndex(b)) counter++
        if (validBIndex(b)) counter++
        if (validUIndex(c)) counter++
        if (validBIndex(c)) counter++
        counter
    }

    private fun validUIndex(from: Int): Boolean = from < diameter

    private fun validBIndex(from: Int): Boolean = from > 0

    private fun toUId(a: Int, b: Int, c: Int): Int {
        return a or (b shl 9) or (c shl 18)
    }
}
