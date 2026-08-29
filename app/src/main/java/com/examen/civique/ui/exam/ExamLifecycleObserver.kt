package com.examen.civique.ui.exam

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner

@Composable
fun ExamLifecycleObserver(
    examStarted: Boolean,
    examFinished: Boolean,
    onAppLeft: () -> Unit
) {

    DisposableEffect(
        examStarted,
        examFinished
    ) {

        val lifecycle =
            ProcessLifecycleOwner
                .get()
                .lifecycle

        val observer =
            LifecycleEventObserver { _, event ->

                if (
                    event ==
                    Lifecycle.Event.ON_STOP &&
                    examStarted &&
                    !examFinished
                ) {

                    onAppLeft()
                }
            }

        lifecycle.addObserver(observer)

        onDispose {

            lifecycle.removeObserver(
                observer
            )
        }
    }
}