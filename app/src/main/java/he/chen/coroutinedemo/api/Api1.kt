package he.chen.coroutinedemo.api

import android.util.Log
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class Api1 {

    suspend fun request(input: String): String? =
        withContext(Dispatchers.IO) {
            suspendCoroutine { cont ->
                OkHttpUtils.request(input, object : OkHttpUtils.CustomCallback {
                    override fun onFailure() {
                        Log.d(TAG, "api1 request failed and return -1 in thread ${Thread.currentThread().name}")
                        cont.resume("-1")
                    }

                    override fun onResponse(res: String?) {
                        val result = if (Random.nextLong(1, 3) == 1L) "-1" else "1"
                        Log.d(TAG, "api1 request responded and return $result in thread ${Thread.currentThread().name}")
                        cont.resume(result)
                    }
                })
            }
        }

    fun fakeAlwaysReturnNewData() = "1"
}