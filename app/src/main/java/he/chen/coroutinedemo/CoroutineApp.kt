package he.chen.coroutinedemo

import android.app.Application
import android.content.Context


class CoroutineApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: CoroutineApp? = null
            private set
        val context: Context
            get() = instance!!.applicationContext
    }
}

