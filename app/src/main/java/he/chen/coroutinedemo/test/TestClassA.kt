package he.chen.coroutinedemo.test

import android.graphics.Color
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class TestClassA {
    private var a: String? = null

    var list: MutableList<in InterfaceTest>? = null

    private fun add(test: Ultra) {
        val a: TypeA<InterfaceTest> = TypeA<Ultra>()
    }
}

class TypeA<in T> {

   fun test(t: T) {}
}

open class Ultra() {
    open fun ultra() {}
}
open class InterfaceTest: Ultra() {
    open fun test() {

    }

    fun test2() {}
}

class Impl1() : InterfaceTest() {
    override fun test() {
        super.test()
    }
}

class Impl2() : InterfaceTest() {}

class Impl3() : InterfaceTest()

fun main() {

}
