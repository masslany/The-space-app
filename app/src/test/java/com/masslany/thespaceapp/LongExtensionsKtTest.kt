package com.masslany.thespaceapp

import com.google.common.truth.Truth.assertThat
import com.masslany.thespaceapp.utils.toCountdownString
import org.junit.Test


internal class LongExtensionsKtTest {

    @Test
    fun futureDateShouldBeCorrectlyDisplayed() {
        // Given
        // 2021-09-01-11:24:32 UTC
        val currentTime = 1630329728101
        // 2021-09-15-00:00:00 UTC
        val futureDate = 1631664000L

        // When
        val countdown = futureDate.toCountdownString(currentTimeInMillis = currentTime)

        // Then
        val expected = "T-15:10:37:52"
        assertThat(countdown).isEqualTo(expected)
    }

    @Test
    fun pastDateShouldBeCorrectlyDisplayed() {
        // Given
        // 2021-09-01-11:24:32 UTC
        val pastDate = 1630329728L
        // 2021-09-15-00:00:00 UTC
        val currentTime = 1631664000000

        // When
        val countdown = pastDate.toCountdownString(currentTimeInMillis = currentTime)

        // Then
        val expected = "T+15:10:37:52"
        assertThat(countdown).isEqualTo(expected)
    }
}