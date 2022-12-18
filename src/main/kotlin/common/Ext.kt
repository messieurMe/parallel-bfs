package common

import java.util.concurrent.RecursiveAction
import kotlin.math.max
import kotlin.math.min

fun fork2join(f1: () -> RecursiveAction, f2: () -> RecursiveAction) {
    val f1Forked = f1().fork()
    val f2Forked = f2().fork()

    f1Forked.join()
    f2Forked.join()

}
