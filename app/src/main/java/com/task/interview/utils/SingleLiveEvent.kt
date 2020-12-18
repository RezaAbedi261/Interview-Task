package com.task.interview.utils

import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer


class SingleLiveEvent<T> : MediatorLiveData<T>() {

    private val observers = ArraySet<ObserverWrapper<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}

//open class SingleLiveEvent<T> : MutableLiveData<T>() {
//
//    private val mPending = AtomicBoolean(false)
//
//    @MainThread
//    override fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<in T>) {
//
//        if (hasActiveObservers()) {
//            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
//        }
//
//        // Observe the internal MutableLiveData
//        super.observe(owner, Observer<T> { t ->
//            if (mPending.compareAndSet(true, false)) {
//                observer.onChanged(t)
//            }
//        })
//    }
//
//    @MainThread
//    override fun setValue(@Nullable t: T?) {
//        mPending.set(true)
//        super.setValue(t)
//    }
//
//    /**
//     * Used for cases where T is Void, to make calls cleaner.
//     */
//    @MainThread
//    fun call() {
//        value = null
//    }
//
//    companion object {
//
//        private const val TAG = "SingleLiveEvent"
//    }
//}