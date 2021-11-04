package busan.swidea.gachijupging.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object TimerViewModel: ViewModel() {
    private var interval: Long = 1
    var count = MutableLiveData<Long>(0)
    private lateinit var job: Job


    fun timerStart() {
        if(::job.isInitialized) {
            job.cancel()
        }

        job = viewModelScope.launch {
            while(true) {
                delay(1000)
                increaseTimerCount()
                Log.d("TEST", "TimerIncreased" + count.value.toString())
            }
        }
    }

    fun timerPause() {
        if(::job.isInitialized) {
            job.cancel()
        }
    }

    fun timerStop() {
        timerPause()
        initTimerCount()
    }

    private fun initTimerCount() {
        count.value = 0
    }

    private fun increaseTimerCount() {
        count.value = count.value?.plus(interval)
    }
}