package graph

interface Graph {

    val size: Int

    fun neighbourNodes(from: Int): List<Int>

    fun neighbourNum(from: Int): Int
//    fun value(node: TNode): Int
}