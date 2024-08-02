package he.chen.coroutinedemo.utils

import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

abstract class A<T> {

}

class B : A<B>() {

}

class CollectMine() {
    val map: MutableMap<KClass<out A<*>>, A<*>> = mutableMapOf()
    fun genericTest(a: A<*>) {
        map[a::class] = a
        when(a) {
            is B -> {}
            else -> {}
        }
    }
}


class MyDelegates(private val a: String) {
    operator fun getValue(hisRef: Any?, property: KProperty<*>): String {
        return property.name + a
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        Log.d("MyDelegates", "$thisRef, ${property.name} = $value")
    }


    class TestComponentClass {
        operator fun invoke(): Int = 3
    }

    fun process(config: Triple<String, String, String>) {
        val (a, b, c) = config
        config.toList().any(TextUtils::isEmpty)
        process("a",
            { s: String -> s == "a" },
            { s, l -> s == "b" }
        )

    }

    fun process(
        result: String,
        a: (String) -> Boolean,
        b: (String, Long) -> Boolean
    ) {

    }

    object Test {
        val state1 = test()

        val state2 get() = test()

        fun test(): Int {
            return System.currentTimeMillis().toInt()
        }
    }

    fun main() {
        val a = Test.state1
        println(a)
        Thread.sleep(200)
        val b = Test.state1
        println(b)
    }

    inline operator fun <reified T> SharedPreferences.getValue(
        thisRef: Any,
        property: KProperty<*>
    ): T {
        return when (T::class) {
            Boolean::class -> getBoolean(property.name, false) as T
            else -> "a" as T
        }
    }

    inline operator fun <reified T> SharedPreferences.setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: T
    ) {
        when (T::class) {
            Boolean::class -> edit().putBoolean(property.name, value as Boolean).apply()
            else -> "a" as T
        }
    }

    val SharedPreferences.delegates get() = SharedPreferencesDelegates(this)

    class SharedPreferencesDelegates(private val prefs: SharedPreferences) {
        fun boolean(
            default: Boolean = false,
            key: String? = null
        ) = create(default, key, prefs::getBoolean, prefs.edit()::putBoolean)

        private fun <T> create(
            default: T,
            key: String? = null,
            getter: (key: String, default: T) -> T,
            setter: (key: String, value: T) -> SharedPreferences.Editor
        ) = object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T {
                return getter(key(key, property), default)
            }

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                setter(key(key, property), value).apply()
            }

            private fun key(key: String?, property: KProperty<*>) = key ?: property.name
        }
    }
}
