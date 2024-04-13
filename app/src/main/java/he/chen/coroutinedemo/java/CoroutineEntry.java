package he.chen.coroutinedemo.java;
import android.content.Context;

import androidx.annotation.Nullable;

import he.chen.coroutinedemo.repo.TestRepo1;
import he.chen.coroutinedemo.repo.TestRepo2;
import he.chen.coroutinedemo.utils.CoroutineHelper;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class CoroutineEntry {

    public static void callSuspendFunction(Context context) {
        if (context != null) {
            CoroutineHelper.call(context);
        }
    }
}
