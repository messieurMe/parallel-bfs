package bfs.seq

import bfs.Bfs
import graph.CubicGraph
import graph.Graph
import java.util.*

class BsfSeq : Bfs {

    override fun findDistances(graph: Graph, start: Int): IntArray {
        val queue = LinkedList<Int>()
        val dist = IntArray(graph.size)
        val visited = Array<Int>(graph.size) { 0 }

        queue.add(start)
        visited[start] = 1
        dist[start] = 0

        while (queue.isNotEmpty()) {
            val next = queue.poll()
            for (i in graph.neighbourNodes(next)) {
                if (visited[i] == 0) {
                    visited[i] = 1
                    queue.add(i)
                    dist[i] = dist[next] + 1
                }
            }
        }
        return dist
    }
}