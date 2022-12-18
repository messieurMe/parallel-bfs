import bfs.par.BfsPar
import bfs.seq.BsfSeq
import graph.CubicGraph
import java.lang.Error
import java.lang.Integer.min
import java.util.LinkedList
import kotlin.random.Random

//-XX:ActiveProcessorCount=4
fun main(args: Array<String>) {
    compare(5)
}

//    val x = AtomicIntegerArray(1_000_000)
//    val scan = PScan()
//    val rrr = scan.scan(x.toIntArray())
//    println(rrr.toList())


//    pfor(0, x.length()) { i ->
//        x.getAndIncrement(i)
//    }

//    for(i in 0 until x.length()){
//        if(x.get(i) != 1){
//            println("HEHE")
//        }
//    }
//    println("OK")

//    val xx = IntArray(10_000) { it }
//    val xx2 = IntArray(10_000) { 0 }
//    pfor(0, 10_000) { i ->
//        xx2[i] += 1
//    }

//    xx2.forEach {
//        if(it != 1){
//            println("AAAAAAAAAA: $it")
//        }
//    }

fun draft() {
    val x1 = 0
    val x2 = 0
    val x3 = 0
    fun uId(a: Int, b: Int, c: Int) = a.or(b.shl(9)).or(c.shl(18))
//    val rr = x1.or(x2.shl(9)).or(x3.shl(18))
    val graph = CubicGraph()

    val st = System.currentTimeMillis()
    val bfs = BfsPar()
    val r = bfs.findDistances(graph, 0)

    println(System.currentTimeMillis() - st)
    println("RES")
    println(r[uId(500, 500, 500)])

    var i = 0
    while (i < r.size) {
        readln()
        val ind1 = i.and(511)
        val ind2 = i.shr(9).and(511)
        val ind3 = i.shr(18).and(511)
        println("Node: $ind1 : $ind2 : $ind3 ; Dist: ${r[i]} ; Sum equals: ${ind1 + ind2 + ind3}")
        i += Random.nextInt(1, 1_000_000)
    }

//    r.forEachIndexed { index, i ->
//        if(index % Random.nextInt(1, 1000))
//        readln()

//        val ind1 = index.and(511)
//        val ind2 = index.shr(9).and(511)
//        val ind3 = index.shr(18).and(511)
//        println("Node: $ind1 : $ind2 : $ind3 ; Dist: $i ")
//    }

//    val a = (r!! % CUBIC_MAX_SIZE)
//    val b = (r / CUBIC_MAX_SIZE) % CUBIC_MAX_SIZE
//    val c = (r / (CUBIC_MAX_SIZE_DOUBLE))
//    println("$a : $b : $c")
}


fun compare(times: Int) {
    val algos = listOf(
        "Par" to BfsPar(),
        "Seq" to BsfSeq()
    )

    algos.forEach { (name, bfs) ->
        var result: IntArray? = null
        val graph = CubicGraph()
        withTime(name, times) {
            bfs.findDistances(graph, 0)
        }
    }
}

fun withTime(name: String, repeat: Int, block: () -> IntArray) {
    println("Block: $name")
    var times = LongArray(repeat)

    var res: IntArray
    for (i in 0 until repeat) {
        val start = System.currentTimeMillis()
        res = block()
        times[i] = System.currentTimeMillis() - start

        verify(res)
    }
    println("\tFull reposrt: ${times.toList()}")
    println("\tAvg: ${times.average()}")
}


fun verify(dist: IntArray) {
    fun uId(a: Int, b: Int, c: Int) = a.or(b.shl(9)).or(c.shl(18))

    for (i in dist.indices) {
        val x1 = i.and(511)
        val x2 = i.shr(9).and(511)
        val x3 = i.shr(18).and(511)

        if (x1 > 499 || x2 > 499 || x3 > 499) {
            continue
        }

        if (dist[i] != x1 + x2 + x3) {
            println("Incorrect distance: D: ${dist[i]}, Xs: $x1 $x2 $x3, XsSum: ${x1 + x2 + x3}")
            throw Error()
        }

        var p1 = if (x1 > 0) dist[uId(x1 - 1, x2, x3)] else Int.MIN_VALUE
        var p2 = if (x2 > 0) dist[uId(x1, x2 - 1, x3)] else Int.MIN_VALUE
        var p3 = if (x3 > 0) dist[uId(x1, x2, x3 - 1)] else Int.MIN_VALUE

        if (x1 + x2 + x3 != 0) {

            if (p1 != dist[i] - 1 && p2 != dist[i] - 1 && p3 != dist[i] - 1) {
                println("Incorrect distance: D: ${dist[i]}, Xs: $x1 $x2 $x3, XsSum: ${x1 + x2 + x3}")
                println("\t$p1 : $p2 : $p3")
                throw Error()
            }

            if (p1 == Int.MIN_VALUE) p1 = Int.MAX_VALUE
            if (p2 == Int.MIN_VALUE) p2 = Int.MAX_VALUE
            if (p3 == Int.MIN_VALUE) p3 = Int.MAX_VALUE
            if (min(p3, min(p1, p2)) != dist[i] - 1) {
                println("Incorrect distance: D: ${dist[i]}, Xs: $x1 $x2 $x3, XsSum: ${x1 + x2 + x3}")
                println("\t$p1 : $p2 : $p3")
                throw Error()
            }
        }
    }
}