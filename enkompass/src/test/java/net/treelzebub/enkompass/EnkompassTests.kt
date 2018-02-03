package net.treelzebub.enkompass

import android.graphics.Typeface
import android.text.style.StyleSpan
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class EnkompassTests {

    @Test
    fun which() {
        val str = "0123456789"
        val substr = "456"
        val which = str.which(substr)

        assertEquals(4..7, which)
    }

    @Test
    fun which_with_span() {
        val str = "test"
        val sub = "est"
        val span = str.toSpannable()
        val which = str.which(sub)

        val bold = StyleSpan(Typeface.BOLD)
        span.enkompass(sub, which.first, which.last, bold)

        assertEquals(which.last, span.getSpanEnd(bold))
    }
}