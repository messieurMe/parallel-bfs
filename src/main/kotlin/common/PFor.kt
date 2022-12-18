package common

import java.util.concurrent.RecursiveAction

private const val THRESHOLD = 5_000

fun pfor(from: Int, to: Int, body: (Int) -> Unit) {
    PFor(from, to, body).fork().join()
}

class PFor(private val from: Int, private val to: Int, private val body: (Int) -> Unit) : RecursiveAction() {
    override fun compute() {
//        println("FT: $from $to; m: ${(from + to) / 2}")
        if (to - from < THRESHOLD) {
            for (i in from until to) {
                body(i)
            }
        } else {
            val m = (from + to) / 2
            fork2join({ PFor(from, m, body) }, { PFor(m, to, body) })
        }
    }
}