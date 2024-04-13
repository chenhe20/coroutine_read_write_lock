package he.chen.coroutinedemo.utils
import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import he.chen.coroutinedemo.CoroutineApp
import kotlinx.coroutines.delay
import java.io.IOException

object FileUtils {

    @WorkerThread
    suspend fun write(fileName: String, data: String) {
    try {
        CoroutineApp.context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
            delay(10000)
            outputStream.write(data.toByteArray())
        }
        Log.d(TAG,"File write successful")
    } catch (e: IOException) {
        e.printStackTrace()
        Log.d(TAG,"Error writing file")
    }
}
    @WorkerThread
    fun read(fileName: String): String? {
    return try {
        CoroutineApp.context.openFileInput(fileName).use { inputStream ->
            inputStream.bufferedReader().use { it.readText() }
        }.also {
            Log.d(TAG,"File read successful")
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Log.d(TAG,"Error reading file")
        null
    }
}

}