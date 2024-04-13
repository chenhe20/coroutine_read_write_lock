package he.chen.coroutinedemo.datasource

import android.util.Log
import androidx.annotation.WorkerThread
import he.chen.coroutinedemo.utils.FileUtils
import he.chen.coroutinedemo.utils.ReadWriteLockFileUtils
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class Repo1LocalDataSource {

    @WorkerThread
    suspend fun readData() = withTimeoutOrNull(1500) {
                // val localData = FileUtils.read(fileName) ?: "local data empty"
                ReadWriteLockFileUtils.read(fileName) ?: "local data empty"
            } ?: "local data read time out"


    fun writeData(data: String) {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
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
    }

    companion object {
        private const val fileName = "data1.json"
    }
}