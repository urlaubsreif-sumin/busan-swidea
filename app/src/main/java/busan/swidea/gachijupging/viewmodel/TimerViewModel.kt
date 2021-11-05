package busan.swidea.gachijupging.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object TimerViewModel: ViewModel() {
    private val intervalMs = 1000L
    var count = Time()
    var paused = false
    private lateinit var job: Job


    fun timerStart() {
        if(::job.isInitialized) {
            job.cancel()
        }

        job = viewModelScope.launch {
            while(true) {
                delay(intervalMs)
                count.increaseOneSecond()
                //Log.d("TEST", "${count.hour.value} ${count.minute.value} ${count.second.value}")
            }
        }
        paused = false
    }

    fun timerPause() {
        if(::job.isInitialized) {
            job.cancel()
        }
        paused = true
    }

    fun timerStop() {
        timerPause()
        count = Time()
        paused = true
    }

    class Time() {
        val hour = MutableLiveData<Int>(0)
        val minute = MutableLiveData<Int>(0)
        val second = MutableLiveData<Int>(0)

        fun increaseOneSecond() {
            if(second.value!! >= 59) {
                second.value = 0
                increaseMinute()
            } else {
                second.value = second.value!! + 1
            }
        }

        private fun increaseMinute() {
            if(minute.value!! >= 59) {
                minute.value = 0
                increaseHour()
            } else {
                minute.value = minute.value!! + 1
            }
        }

        private fun increaseHour() {
            if(hour.value!! >= 24) {
                hour.value = 0
            } else {
                hour.value = hour.value!! + 1
            }
        }
    }
}