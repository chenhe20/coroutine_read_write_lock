package he.chen.coroutinedemo.repo

import he.chen.coroutinedemo.api.Api2

class TestRepo2 {

    private val api2 = Api2()

    suspend fun getData2(string: String): String? = api2.request(string)
}