package he.chen.coroutinedemo.utils

import android.content.Context
import android.util.Log
import he.chen.coroutinedemo.repo.TestRepo1
import he.chen.coroutinedemo.repo.TestRepo2
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object CoroutineHelper {

    @JvmStatic
    fun call(context: Context) {
        MainScope().launch {
            doSuspend(context)
        }
    }

    suspend fun doSuspend(context: Context) {
        Log.d(TAG, "try get data1 in thread ${Thread.currentThread().name}")
        val data1 = TestRepo1().getData1(context)
        Log.d(TAG, "try get data2 in thread ${Thread.currentThread().name}")
        val data2 = TestRepo2().getData2(data1)
        Log.d(TAG, "inside scope done in ${Thread.currentThread().name}")
    }
}