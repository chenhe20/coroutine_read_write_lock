package he.chen.coroutinedemo.repo

class TestRepo3 {

    private val cache: Cache? = null
    fun getTimestamp() =
        if (cache == null) readFromFile()?.timestamp
        else cache.timestamp

    fun readFromFile(): Cache? = Cache()

    fun hasValidCache(timestamp: Long): Boolean {
        return timestamp == getTimestamp()
    }

    fun updateWithTimestamp(timestamp: Long) {
        if (getTimestamp() == timestamp) {
            // do nothing
        } else {
            requestWithTimeStamp(timestamp)
        }
    }

    fun requestWithTimeStamp(timestamp: Long) {}


    class Cache {
        val data: String = "local data"
        val timestamp = 0L
    }
}