package he.chen.coroutinedemo.repo

import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import he.chen.coroutinedemo.api.Api1
import he.chen.coroutinedemo.datasource.Repo1LocalDataSource
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class TestRepo1 {

    // a good code

    private val api1 = Api1()
    private val localDataSource = Repo1LocalDataSource()

    @Volatile
    private var _data: String? = null

    private val data: String
        get() {
            if (_data == null) {
                synchronized(this) {
                    if (_data == null) {
                        _data = localDataSource.getData()  // 确保只加载一次
                    }
                }
            }
            return _data!!
        }

    init {
        Thread {
            _data = localDataSource.getData()
        }.start()
    }

    @WorkerThread
    private suspend fun getData1(): String {
        //val result = api1.request("a")
        val result = api1.fakeAlwaysReturnNewData()
        return result.let{
            Log.d(TAG, "repo 1 get data = $result from server in ${Thread.currentThread().name}")
            if (it == "-1") data
            else {
                Log.d(TAG, "call write local datasource 1 function in thread ${Thread.currentThread().name}")
                MainScope().launch{ localDataSource.writeData(it) }
                it
            }
        }
    }

    @MainThread
    suspend fun model1ToData1(
        data: String,
        context: Context?
    ) =
        withContext(Dispatchers.Main) {
            Log.d(TAG, "validated data $data in thread ${Thread.currentThread().name}")
            data
        }

    suspend fun getData1(context: Context?) = model1ToData1(getData1(), context)


}