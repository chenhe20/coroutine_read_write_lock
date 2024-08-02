package he.chen.coroutinedemo

import android.graphics.Color
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun timeCosting_fun_should_not_block() {
        runBlocking {
            doAsyncTest()
        }
    }


    @Test
    fun should_print_hello_world() {
        runBlocking {
            doWorld()
        }
    }

    @Test
    fun should_not_equal_to_minus_one() {
        val parseColor = Color.parseColor("FFFFFF")
        assertEquals(parseColor, -1)
    }

    suspend fun doWorld() = coroutineScope {  // this: CoroutineScope
        launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
    }

    private suspend fun doAsyncTest() {
        print( "starts in thread ${Thread.currentThread().name}")
        coroutineScope {
            launch { veryTimeCosting() }
            print("ends in thread ${Thread.currentThread().name}")
        }
    }

    private suspend fun veryTimeCosting() {
        delay(10_000L)
        print("time costing finished in thread ${Thread.currentThread().name}")
    }
}

const val TAG = "test"