package he.chen.coroutinedemo.repo

import android.content.Context
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import he.chen.coroutinedemo.api.Api1
import he.chen.coroutinedemo.datasource.Repo1LocalDataSource
import he.chen.coroutinedemo.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestRepo1 {

    private val api1 = Api1()
    private val localDataSource = Repo1LocalDataSource()

    @WorkerThread
    private suspend fun getData1(): String {
        //val result = api1.request("a")
        val result = api1.fakeAlwaysReturnNewData()
        return result?.let{
            Log.d(TAG, "repo 1 get data = $result from server in ${Thread.currentThread().name}")
            if (it == "-1") localDataSource.readData()
            else {
                Log.d(TAG, "call write local datasource 1 function in thread ${Thread.currentThread().name}")
                localDataSource.writeData(it)
                it
            }
        } ?: localDataSource.readData()
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