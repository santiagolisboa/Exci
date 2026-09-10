package com.examen.civique.ui.splash

import kotlin.math.sin

data class BrandSplashFrame(
    val logoAlpha: Float,
    val logoScale: Float,
    val pigeonAlpha: Float,
    val pigeonFrame: Int,
    val pigeonOffsetX: Float,
    val pigeonOffsetY: Float,
    val pigeonScaleY: Float,
    val contentAlpha: Float,
    val finished: Boolean
)

object BrandSplashTimeline {
    const val DURATION_MILLIS = 2_100L
    const val REDUCED_MOTION_DURATION_MILLIS = 350L

    fun frameAt(elapsedMillis: Long, reducedMotion: Boolean): BrandSplashFrame {
        val elapsed = elapsedMillis.coerceAtLeast(0L)
        if (reducedMotion) {
            val alpha = when {
                elapsed < 120L -> elapsed / 120f
                elapsed < 260L -> 1f
                else -> ((REDUCED_MOTION_DURATION_MILLIS - elapsed) / 90f).coerceIn(0f, 1f)
            }
            return BrandSplashFrame(alpha, 1f, 0f, 0, 0f, 0f, 1f, alpha, elapsed >= REDUCED_MOTION_DURATION_MILLIS)
        }

        val logoAlpha = when {
            elapsed < 150L -> 0f
            elapsed < 450L -> (elapsed - 150L) / 300f
            elapsed < 900L -> 1f
            elapsed < 1_050L -> (1_050L - elapsed) / 150f
            else -> 0f
        }
        val pigeonEntranceAlpha = ((elapsed - 1_150L) / 80f).coerceIn(0f, 1f)
        val pigeonExitAlpha = ((DURATION_MILLIS - elapsed) / 150f).coerceIn(0f, 1f)
        val pigeonAlpha = minOf(pigeonEntranceAlpha, pigeonExitAlpha)
        val frame = when {
            elapsed < 1_300L -> 0
            elapsed < 1_450L -> 1
            elapsed < 1_550L -> 2
            elapsed < 1_650L -> 3
            elapsed < 1_740L -> 4
            elapsed < 1_800L -> 5
            else -> 6
        }
        val crowing = elapsed in 1_450L..1_800L
        val shake = if (crowing) sin(elapsed / 18.0).toFloat() * 3.5f else 0f
        val lift = if (crowing) -6f else 0f
        return BrandSplashFrame(
            logoAlpha = logoAlpha,
            logoScale = 0.985f + logoAlpha * 0.015f,
            pigeonAlpha = pigeonAlpha,
            pigeonFrame = frame,
            pigeonOffsetX = shake,
            pigeonOffsetY = lift,
            pigeonScaleY = if (crowing) 1.025f else 1f,
            contentAlpha = 1f,
            finished = elapsed >= DURATION_MILLIS
        )
    }
}
