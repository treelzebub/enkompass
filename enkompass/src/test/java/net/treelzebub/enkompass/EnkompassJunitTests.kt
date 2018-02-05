package net.treelzebub.enkompass

import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class EnkompassJunitTests {

    @Test fun equality() {
        val str = "Test string"
        val sub = "string"
        val spannable = str.enkompass(sub) {}

        assertEquals(str, spannable.toString())
    }

    @Test fun which() {
        val str = "0123456789"
        val sub = "456"
        val which = str.which(sub)

        assertEquals(4 until 7, which)
    }

    @Test fun which_with_span() {
        val str = "test"
        val sub = "est"
        val which = str.which(sub)

        val span = str.enkompass(sub) { bold() }

        val result = span.getSpans(which.first, which.last - 1, StyleSpan::class.java)
        assertNotNull(result)
    }

    @Test fun substring_strategy() {
        val str = "funky fresh"
        val sub = "fresh"

        val bold = StyleSpan(Typeface.BOLD)
        val spannable = str.enkompass(Enkompass.SubstringStrategy(str, sub), bold)

        val result = str.which(sub).let {
            spannable.getSpans(it.first, it.last, StyleSpan::class.java).first()
        }

        assertEquals(bold, result)
    }

    @Test fun intrange_strategy() {
        val str = "stinky wizzleteats"
        val sub = "stinky"

        val color = ForegroundColorSpan(0)
        val spannable = str.enkompass(Enkompass.SubstringStrategy(str, sub), color)

        val result = str.which(sub).let {
            spannable.getSpans(it.first, it.last, ForegroundColorSpan::class.java).first()
        }

        assertEquals(color, result)
    }

    @Test fun spans() {
        val str = "Test string"
        val sub = "string"
        val spannable = str.enkompass(sub) {
            bold()
            italics()
            boldItalics()
            monospace()
        }

        val bold = StyleSpan(Typeface.BOLD).wrap()
        val italics = StyleSpan(Typeface.ITALIC).wrap()
        val boldItalics = StyleSpan(Typeface.BOLD_ITALIC).wrap()
        val monospace = TypefaceSpan("monospace").wrap()

        val all = str.which(sub).let { spannable.getSpans(it.first, it.last, Any::class.java) }
        assertTrue(bold in all)
        assertTrue(italics in all)
        assertTrue(boldItalics in all)
        assertTrue(monospace in all)
    }


    private fun StyleSpan.wrap() = StyleWrapper(this)
    private class StyleWrapper(private val style: StyleSpan) : StyleSpan(style.style) {
        override fun equals(other: Any?) = other is StyleSpan && style.style == other.style
        override fun hashCode() = style.hashCode()
    }

    private fun TypefaceSpan.wrap() = TypefaceWrapper(this)
    private class TypefaceWrapper(private val typeface: TypefaceSpan) : TypefaceSpan(typeface.family) {
        override fun equals(other: Any?) = other is TypefaceSpan && typeface.family == other.family
        override fun hashCode() = typeface.hashCode()
    }
}