package he.chen.coroutinedemo.datasource

import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.WorkerThread
import he.chen.coroutinedemo.utils.MyDelegates
import he.chen.coroutinedemo.utils.ReadWriteLockFileUtils
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.reflect.KProperty

class Repo1LocalDataSource {

    private lateinit var sharedPreferences: SharedPreferences

//    private var data: Boolean by sharedPreferences.delegates.boolean()
//    private val data2 by MyDelegates()

    @WorkerThread
    suspend fun readData() = withTimeoutOrNull(1500) {
                // val localData = FileUtils.read(fileName) ?: "local data empty"
                ReadWriteLockFileUtils.read(fileName) ?: "local data empty"
            } ?: "local data read time out"

    fun getData(): String {
        Thread.sleep(1)
        return "local data 1"
    }

    suspend fun writeData(data: String) {
            val startTime = System.currentTimeMillis()
            Log.d(TAG, "start writing local datasource 1 function in thread ${Thread.currentThread().name}")
            try {
                //val localData = FileUtils.write(fileName, data)
                 ReadWriteLockFileUtils.write(fileName, data)
            } catch (e: Exception) {
                Log.e(TAG, "写入异常：$e")
            }
            Log.d(TAG, "local datasource 1 finished writing data into file in thread ${Thread.currentThread().name}, time cost is ${System.currentTimeMillis() - startTime}")
    }

    companion object {
        private const val fileName = "data1.json"
    }
}
class MyLazy<out T: Any?>(
    private val initializer: () -> T
) {

    private var value: T? = null
    operator fun getValue(hisRef: Any?, property: KProperty<*>): T {
        return if (value == null) {
            value = initializer()
            value!!
        } else value!!
    }

    // a bad code

    // some other code here
}