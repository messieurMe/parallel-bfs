package bfs.par

import bfs.Bfs
import common.PScan
import common.pfor
import graph.Graph
import java.lang.Integer.max
import java.util.concurrent.atomic.AtomicIntegerArray


class BfsPar : Bfs {
    override fun findDistances(graph: Graph, start: Int): IntArray {
        val dist = IntArray(graph.size)
        val visited = AtomicIntegerArray(graph.size)
        visited.set(0, 1)

        var frontier = List(1) { start }

        var currDist = 0

        while (frontier.isNotEmpty()) {
            currDist++

            var deg = IntArray(frontier.size)
            pfor(0, frontier.size) { i -> deg[i] = graph.neighbourNum(frontier[i]) }
            deg = PScan().scan(deg)
            val size = deg.last()

            val nextFrontier = IntArray(size) { -1 }

            pfor(0, frontier.size) { i ->
                val nextNodes = graph.neighbourNodes(frontier[i])
                for (j in nextNodes.indices) { // for instead of pfor cause threshold > 10_000
                    val nextNode = nextNodes[j]
                    if (visited.compareAndSet(nextNode, 0, 1)) {
                        dist[nextNode] = currDist
                        nextFrontier[deg[i] + j] = nextNode
                    }
                }
            }
            frontier = nextFrontier.filter { it != -1 }
        }
        return dist
    }
}