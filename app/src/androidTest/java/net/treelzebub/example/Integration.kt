package net.treelzebub.example

import android.graphics.Typeface
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.text.Spannable
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.widget.TextView
import junit.framework.Assert.assertTrue
import net.treelzebub.enkompass.enkompass
import net.treelzebub.enkompass.example.MainActivity
import net.treelzebub.enkompass.example.R
import net.treelzebub.enkompass.which
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class Integration {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun dsl() {

        val textview = activityRule.activity.findViewById<TextView>(R.id.textview)!!
        val bold = StyleSpan(Typeface.BOLD).wrap()
        val italics = StyleSpan(Typeface.ITALIC).wrap()
        val boldItalics = StyleSpan(Typeface.BOLD_ITALIC).wrap()
        val monospace = TypefaceSpan("monospace").wrap()

        var flag = false

        val str = "Test string"
        val sub = "string"
        val spannable = str.enkompass(sub) {
            bold()
            italics()
            boldItalics()
            monospace()
            clickable(textview) { flag = true }
        }

        val all = str.which(sub).let { spannable.getSpans(it.first, it.last, Any::class.java) }
        assertTrue(bold in all)
        assertTrue(italics in all)
        assertTrue(boldItalics in all)
        assertTrue(monospace in all)

        val clickable = spannable.findSpan<ClickableSpan>(sub)
        clickable.onClick(textview)
        assertTrue(flag)
    }

    private inline fun <reified Span : Any> Spannable.findSpan(substring: String): Span {
        val which = which(substring)
        val spans = getSpans(which.first, which.last, Span::class.java)
        return spans.find { it is Span }!!
    }
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
