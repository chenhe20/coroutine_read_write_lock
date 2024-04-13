package he.chen.coroutinedemo.utils

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import he.chen.coroutinedemo.CoroutineApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.IOException
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
object ReadWriteLockFileUtils {

    private val lock = CustomReadWriteLock()

    @WorkerThread
    suspend fun write(fileName: String, data: String) {
        lock.write {
            try {
                CoroutineApp.context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                    delay(10000)
                    outputStream.write(data.toByteArray())
                }
                Log.d(TAG, "File write successful")
            } catch (e: IOException) {
                Log.d(TAG, "Error writing file")
            }
        }
    }

    @WorkerThread
    suspend fun read(fileName: String): String? =
        withContext(Dispatchers.IO) {
            lock.read {
                try {
                    CoroutineApp.context.openFileInput(fileName).use { inputStream ->
                        inputStream.bufferedReader().use { it.readText() }
                    }.also {
                        Log.d(TAG, "File read successful")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.d(TAG, "Error reading file")
                    null
                }
            }
        }
}
