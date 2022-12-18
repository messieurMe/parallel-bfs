package common

import java.util.concurrent.RecursiveAction

private const val THRESHOLD = 10_000

class PScan {
    private class Tree(
        var value: Int,
        var l: Tree? = null,
        var r: Tree? = null
    )

    private class FirstIteration(
        val l: Int,
        val r: Int,
        val node: Tree,
        val array: IntArray
    ) : RecursiveAction() {

        override fun compute() {
            if (r - l < THRESHOLD) {
//                var sum = 0
                for (i in l..r) {
                    node.value += array[i]
                }
//                node.value = sum
            } else {
                val m = (l + r) / 2
                // Тут нужно создавать левую и правую ноду, но я это опустил
                node.l = Tree(0, null, null)
                node.r = Tree(0, null, null)
                fork2join(
                    { FirstIteration(l, m, node.l!!, array) },
                    { FirstIteration(m + 1, r, node.r!!, array) }
                )
                node.value = node.l!!.value + node.r!!.value
            }
        }
    }

    private class SecondIteration(
        private val l: Int,
        private val r: Int,
        private val prefix: Int,
        private val node: Tree,
        private val array: IntArray,
        private val result: IntArray
    ) : RecursiveAction() {

        override fun compute() {
            if (r - l < THRESHOLD) {
                result[l + 1] = prefix + array[l]
                for (i in l + 1..r) {
                    result[i + 1] = array[i] + result[i]
                }
//                result[l] = prefix + array[l]
            } else {
                val m = (l + r) / 2
                fork2join(
                    { SecondIteration(l, m, prefix, node.l!!, array, result) },
                    { SecondIteration(m + 1, r, prefix + node.l!!.value, node.r!!, array, result) }
                )
            }
        }
    }

    fun scan(array: IntArray): IntArray {
        val tree = Tree(0)
        val result = IntArray(array.size + 1)

        FirstIteration(0, array.size - 1, tree, array).fork().join()
        SecondIteration(0, array.size - 1, 0, tree, array, result).fork().join()

        return result
    }

}



