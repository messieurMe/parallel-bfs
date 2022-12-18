package bfs

import graph.Graph


interface Bfs {
    fun findDistances(graph: Graph, start: Int): IntArray
}