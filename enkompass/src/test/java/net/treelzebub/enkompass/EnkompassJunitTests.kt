package net.treelzebub.enkompass

import android.graphics.Typeface
import android.text.style.StyleSpan
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(RobolectricTestRunner::class)
class EnkompassJunitTests {

    @Test
    fun which() {
        val str = "0123456789"
        val sub = "456"
        val which = str.which(sub)

        assertEquals(4..7, which)
    }

    @Test
    fun which_with_span() {
        val str = "test"
        val sub = "est"
        val which = str.which(sub)

        val span = str.toSpannable()
        val bold = StyleSpan(Typeface.BOLD)
        span.enkompass(sub, bold)

        assertEquals(which.last, span.getSpanEnd(bold))
    }

    @Test fun equality() {
        val str = "Test string"
        val sub = "string"
        val spannable = str.enkompass(sub)

        Assert.assertEquals(str, spannable.toString())
    }

    @Test
    fun throws() {
        val str = "test string"
        val sub = "i'm not in the test string."
        assertFailsWith<IllegalArgumentException> { str.enkompass(sub) }
    }
}