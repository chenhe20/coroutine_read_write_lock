package he.chen.coroutinedemo.mytest

import java.util.Timer

class AAAA {
    fun exampleFunction(callback: ((bool: Boolean) -> Unit)? = null) {
        callback?.invoke(true)

        Timer().purge()
    }
}