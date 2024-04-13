package he.chen.coroutinedemo.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import okhttp3.internal.notifyAll

class CustomReadWriteLock {
    private val mutex = Mutex()
    private var readers = 0
    private val noReaders = Channel<Unit>(1)  // 用于控制没有读者时通知写操作

    suspend fun <T> read(action: suspend () -> T): T {
        mutex.withLock {
            readers++
        }
        try {
            return action()
        } finally {
            mutex.withLock {
                readers--
                if (readers == 0) {
                    noReaders.trySend(Unit)  // 读取者为0，通知写操作可以继续
                }
            }
        }
    }

    suspend fun <T> write(action: suspend () -> T): T {
        mutex.withLock {
            if (readers > 0) {
                noReaders.receive()  // 如果还有读者，等待读者完成
            }
        }

        return mutex.withLock {
            try {
                action()
            } finally {
                if (readers == 0) {
                    noReaders.trySend(Unit)  // 完成写操作后，如果没有读者，重置状态
                }
            }
        }
    }
}
