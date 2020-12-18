package com.task.interview.utils

import androidx.lifecycle.MutableLiveData
import com.task.interview.BuildConfig
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext

object TimeUtil {

    val currentCalendar = object : MutableLiveData<Calendar>() {
        var job: Deferred<Unit>? = null
        inline val self get() = this
        private val supervisorJob = SupervisorJob(EmptyCoroutineContext[Job])
        private val scope = CoroutineScope(Dispatchers.Main.immediate + EmptyCoroutineContext + supervisorJob)

        override fun onActive() {
            value?.let { return }

            scope.launch(Dispatchers.Main) {
                while (isActive){
                    value = Calendar.getInstance()
                    delay(60_000L) //TODO: add config
                }
            }
        }

        override fun onInactive() {
            super.onInactive()
            job?.cancel()
        }
    }

    val isNight : Boolean get(){

        // TODO: 9/16/20 remove this line
        if (BuildConfig.DEBUG){
            return false
        }
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        return timeOfDay < 6 || timeOfDay > 18 //TODO: add hours to config
    }

//    val isNightLiveData = currentCalendar.map { c ->
//                val timeOfDay = c[Calendar.HOUR_OF_DAY]
//                timeOfDay < 6 || timeOfDay > 18 //TODO: add hours to config
//            }
//            .distinctUntilChanged()

}