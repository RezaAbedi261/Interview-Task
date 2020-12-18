package com.task.interview.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.task.interview.utils.fromJson
import com.task.interview.utils.json
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST", "unused")
abstract class PrefsWrapper(private var context: Context) {
    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("${context.packageName}.${javaClass.simpleName}", Context.MODE_PRIVATE)
    }

    private val listeners = mutableListOf<SharedPrefsListener>()

    abstract class PrefDelegate<T>(val prefKey: String?) {
        abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
        abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
    }

    interface SharedPrefsListener {
        fun onSharedPrefChanged(property: KProperty<*>)
    }

    fun addListener(sharedPrefsListener: SharedPrefsListener) {
        listeners.add(sharedPrefsListener)
    }

    fun removeListener(sharedPrefsListener: SharedPrefsListener) {
        listeners.remove(sharedPrefsListener)
    }

    fun clearListeners() = listeners.clear()

    enum class StorableType {
        String,
        Int,
        Float,
        Boolean,
        Long,
        StringSet,
        Object
    }

    inner class GenericPrefDelegate<T : Any>(prefKey: String? = null, private val defaultValue: T?, private val type: StorableType, private val cls: KClass<T>? = null) :
        PrefDelegate<T?>(prefKey) {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T? =
            when (type) {
                StorableType.String ->
                    prefs.getString(prefKey ?: property.name, defaultValue as String?) as T?
                StorableType.Int ->
                    prefs.getInt(prefKey ?: property.name, defaultValue as Int) as T?
                StorableType.Float ->
                    prefs.getFloat(prefKey ?: property.name, defaultValue as Float) as T?
                StorableType.Boolean ->
                    prefs.getBoolean(prefKey ?: property.name, defaultValue as Boolean) as T?
                StorableType.Long ->
                    prefs.getLong(prefKey ?: property.name, defaultValue as Long) as T?
                StorableType.StringSet ->
                    prefs.getStringSet(prefKey ?: property.name, defaultValue as Set<String>) as T?
                StorableType.Object ->
                    prefs.getString(prefKey ?: property.name, "")?.fromJson<T>(cls!!.java) ?: defaultValue
            }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            when (type) {
                StorableType.String -> {
                    prefs.edit().putString(prefKey ?: property.name, value as String?).apply()
                    onPrefChanged(property)
                }
                StorableType.Int -> {
                    prefs.edit().putInt(prefKey ?: property.name, value as Int).apply()
                    onPrefChanged(property)
                }
                StorableType.Float -> {
                    prefs.edit().putFloat(prefKey ?: property.name, value as Float).apply()
                    onPrefChanged(property)
                }
                StorableType.Boolean -> {
                    prefs.edit().putBoolean(prefKey ?: property.name, value as Boolean).apply()
                    onPrefChanged(property)
                }
                StorableType.Long -> {
                    prefs.edit().putLong(prefKey ?: property.name, value as Long).apply()
                    onPrefChanged(property)
                }
                StorableType.StringSet -> {
                    prefs.edit().putStringSet(prefKey ?: property.name, value as Set<String>).apply()
                    onPrefChanged(property)
                }
                StorableType.Object -> {
                    prefs.edit().putString(prefKey ?: property.name, (value as? Any)?.json ?: "").apply()
                    onPrefChanged(property)
                }
            }
        }

    }

    fun stringPref(defaultValue: String? = null, prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.String)

    fun intPref(defaultValue: Int = 0, prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Int)

    fun floatPref(defaultValue: Float = 0f, prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Float)

    fun booleanPref(defaultValue: Boolean = false, prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Boolean)

    fun longPref(defaultValue: Long = 0L, prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Long)

    fun stringSetPref(defaultValue: Set<String> = HashSet(), prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.StringSet)

    inline fun <reified T : Any> pref(defaultValue: T? = null, prefKey: String? = null) =
        GenericPrefDelegate(prefKey, defaultValue, StorableType.Object, T::class)

    private fun onPrefChanged(property: KProperty<*>) {
        listeners.forEach { it.onSharedPrefChanged(property) }
    }
}