package com.examen.civique.ui.splash

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BrandSplashTimelineTest {
    @Test
    fun normalTimelineReachesEveryKeyStateAndFinishes() {
        assertEquals(0f, BrandSplashTimeline.frameAt(0, false).logoAlpha)
        assertEquals(1f, BrandSplashTimeline.frameAt(450, false).logoAlpha)
        assertEquals(0f, BrandSplashTimeline.frameAt(1_050, false).logoAlpha)
        assertEquals(0f, BrandSplashTimeline.frameAt(1_150, false).pigeonAlpha)
        assertEquals(0, BrandSplashTimeline.frameAt(1_200, false).pigeonFrame)
        assertEquals(4, BrandSplashTimeline.frameAt(1_700, false).pigeonFrame)
        assertEquals(6, BrandSplashTimeline.frameAt(1_850, false).pigeonFrame)
        assertFalse(BrandSplashTimeline.frameAt(2_099, false).finished)
        assertTrue(BrandSplashTimeline.frameAt(2_100, false).finished)
    }

    @Test
    fun exciAndPigeonAreNeverVisibleAtTheSameTime() {
        for (elapsed in 0L..BrandSplashTimeline.DURATION_MILLIS) {
            val frame = BrandSplashTimeline.frameAt(elapsed, reducedMotion = false)
            assertFalse(
                "EXCI and pigeon overlap at ${elapsed}ms",
                frame.logoAlpha > 0f && frame.pigeonAlpha > 0f
            )
        }
    }

    @Test
    fun reducedMotionUsesShortFadeWithoutPigeonAnimation() {
        val middle = BrandSplashTimeline.frameAt(180, true)
        assertEquals(0f, middle.pigeonAlpha)
        assertEquals(0, middle.pigeonFrame)
        assertFalse(middle.finished)
        assertTrue(BrandSplashTimeline.frameAt(350, true).finished)
    }
}
