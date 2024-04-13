package he.chen.coroutinedemo.api

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object OkHttpUtils {

    fun request(
        input: String,
        callback: CustomCallback
    ) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.google.com")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onResponse("-1") // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                val responseData = response.body?.string() // Extract response body as string
                print(responseData)
                response.body?.close()
                callback.onResponse("-1")
            }
        })
    }

    interface CustomCallback {
        fun onFailure()
        fun onResponse(res: String?)
    }
}