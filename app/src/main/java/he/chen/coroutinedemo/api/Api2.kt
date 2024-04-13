package he.chen.coroutinedemo.api

import android.util.Log
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Api2 {

    suspend fun request(input: String): String? =
        withContext(Dispatchers.IO) {
            suspendCoroutine { cont ->
                OkHttpUtils.request(input, object : OkHttpUtils.CustomCallback {
                    override fun onFailure() {
                        Log.d(TAG, "api2 request failed and return -1 in thread ${Thread.currentThread().name}")
                        cont.resume("-1")
                    }

                    override fun onResponse(res: String?) {
                        Log.d(TAG, "api2 request responded and return -1 in thread ${Thread.currentThread().name}")
                        cont.resume("-1")
                    }
                })
            }
        }


}