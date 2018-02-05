package net.treelzebub.example

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.style.*
import android.widget.TextView
import net.treelzebub.enkompass.enkompass
import net.treelzebub.enkompass.example.MainActivity
import net.treelzebub.enkompass.example.R
import net.treelzebub.enkompass.which
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue


@RunWith(AndroidJUnit4::class)
class Integration {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private val activity: MainActivity
        get() = activityRule.activity

    private val textview: TextView
        get() = activity.findViewById(R.id.textview_string)!!

    @Test fun clickable() {
        val str = "Test string"
        val sub = "string"

        var flag = false
        val spannable = str.enkompass(sub) {
            clickable(textview) { flag = true}
        }

        val clickable = spannable.findSpan<ClickableSpan>(sub)
        clickable.onClick(textview)
        assertTrue(flag)
    }

    @Test fun foregroundColor() {
        val str = "Gabba gabba hey"
        val sub = "hey"
        val black = ContextCompat.getColor(activity, android.R.color.black)

        val spannable = str.enkompass(sub) {
            foregroundColor(black)
        }

        val span = spannable.findSpan<ForegroundColorSpan>(sub)
        assertEquals(black, span.foregroundColor)
    }

    @Test fun backgroundColor() {
        val str = "Gabba gabba hey"
        val sub = "hey"
        val black = ContextCompat.getColor(activity, android.R.color.black)

        val spannable = str.enkompass(sub) {
            backgroundColor(black)
        }

        val span = spannable.findSpan<BackgroundColorSpan>(sub)
        assertEquals(black, span.backgroundColor)
    }

    @Test fun image() {
        val str = "i'm hungry"
        val sub = "hungry"
        val drawable = ContextCompat.getDrawable(activity, android.R.drawable.ic_dialog_info)!!

        val spannable = str.enkompass(sub) {
            image(drawable, true)
        }

        val span = spannable.findSpan<ImageSpan>(sub)

        assertEquals(drawable, span.drawable)
        assertEquals(ImageSpan.ALIGN_BASELINE, span.verticalAlignment)
    }

    @Test fun size() {
        val str = "size matters up in here"
        val sub = "matters"
        val prop = 5f

        val spannable = str.enkompass(sub) { size(prop) }
        val span = spannable.findSpan<RelativeSizeSpan>(sub)

        assertEquals(prop, span.sizeChange)
    }

    @Test fun custom() {
        val str = "ride johnny, ride"
        val sub = "johnny"

        val spannable = str.enkompass(sub) {
            custom {
                BulletSpan()
                AbsoluteSizeSpan(0)
            }
        }

        val which = str.which(sub)
        val spans = spannable.getSpans(which.first, which.last-1, Any::class.java)

        assert(spans.any { it is BulletSpan })
        assert(spans.any { it is AbsoluteSizeSpan })
    }

    @Test fun findSpan() {
        val str = "poopie doo"
        val sub = "doo"

        val spannable = str.enkompass(sub) {}

        assertFailsWith<NullPointerException> {
            spannable.findSpan<ImageSpan>(sub)
        }
    }

    private inline fun <reified Span : Any> Spannable.findSpan(substring: String): Span {
        val which = which(substring)
        val spans = getSpans(which.first, which.last, Span::class.java)
        return spans.find { it is Span }!!
    }
}

