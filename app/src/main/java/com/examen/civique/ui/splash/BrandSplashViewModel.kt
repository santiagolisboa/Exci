package com.examen.civique.ui.splash

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BrandSplashViewModel : ViewModel() {
    val startedAtElapsedRealtime: Long = SystemClock.elapsedRealtime()

    private val _finished = MutableStateFlow(false)
    val finished: StateFlow<Boolean> = _finished.asStateFlow()

    fun elapsedMillis(): Long = SystemClock.elapsedRealtime() - startedAtElapsedRealtime

    fun finish() {
        _finished.value = true
    }
}
