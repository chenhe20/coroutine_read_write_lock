package he.chen.coroutinedemo.mytest

class Child : Father {

    constructor(a: Int, b: Boolean) : super(a, b) {
        println("child constructor 1 is invoked")
    }

    constructor(a: Int, b: Boolean, c: Float) : super(a, b, c) {
        println("child constructor 2 is invoked")
    }


}
fun main() {
    Child(1, true)
    println("====")
    Child(1, true, 1f)
}