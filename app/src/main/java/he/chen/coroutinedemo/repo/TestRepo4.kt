package he.chen.coroutinedemo.repo

import java.sql.Timestamp
import kotlin.concurrent.Volatile

class TestRepo4 {

    @Volatile
    private var _cache: Cache? = null
    private val cache: Cache
        get() {
            if (_cache == null) {
                synchronized(this) {
                    if (_cache == null) {
                        _cache = getData()
                    }
                }
            }
            return _cache!!
        }

    fun getData() = Cache()

    fun updateCacheWithTimestamp(timestamp: Long) {
        if (cache.timestamp == timestamp) {
            // do nothing
        } else {
            requestWithTimestamp(timestamp)
        }
    }

    fun requestWithTimestamp(timestamp: Long) {}

    init {
        _cache = getData()
    }

}

class Cache() {
    val data: String = "local data"
    val timestamp: Long = 0L

}