package net.treelzebub.enkompass

import android.graphics.Typeface
import android.text.style.StyleSpan
import android.widget.TextView
import net.treelzebub.enkompass.dsl.enkompass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class EnkompassTests {

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

    @Test
    fun dsl() {
        val str = "asdfasdfasdf"
        val sub = "asdf"

        val textview = TextView(RuntimeEnvironment.application)

        str.enkompass(sub) {
            bold(it)
            italics(it)
            boldItalics(it)
            clickable(textview, it) {}
        }

    }
}