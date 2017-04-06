package net.treelzebub.enkompass

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorRes
import android.support.annotation.StyleRes
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import android.text.style.TypefaceSpan
import android.view.View
import net.treelzebub.enkompass.spans.ClickSpan


/**
 * Created by Tre Murillo on 4/5/17
 *
 * Quickly and easily style parts or all of Strings.
 */


/**
 * Feed it a substring and get the range of indices in the outer string.
 *
 * @param substring  a substring contained in the extended CharSequence. Will explode if
 *                   substring does not exist.
 */
fun CharSequence.which(substring: String): IntRange {
    return toString().let {
        val start = it.indexOf(substring)
        val end = it.lastIndexOf(substring)
        IntRange(start, end)
    }
}

fun String.toSpannable() = SpannableStringBuilder(this)

fun SpannableStringBuilder.style(c: Context, substring: String, @StyleRes style: Int)
        = enkompass(substring, TextAppearanceSpan(c, style))

fun SpannableStringBuilder.normal(substring: String)
        = enkompass(substring, StyleSpan(Typeface.NORMAL))

fun SpannableStringBuilder.bold(substring: String)
        = enkompass(substring, StyleSpan(Typeface.BOLD))

fun SpannableStringBuilder.italic(substring: String)
        = enkompass(substring, StyleSpan(Typeface.ITALIC))

fun SpannableStringBuilder.boldItalic(substring: String)
        = enkompass(substring, StyleSpan(Typeface.BOLD_ITALIC))

fun SpannableStringBuilder.monospace(substring: String)
        = enkompass(substring, TypefaceSpan("monospace"))

fun SpannableStringBuilder.colorize(substring: String, @ColorRes color: Int)
        = enkompass(substring, ForegroundColorSpan(color))

fun SpannableStringBuilder.clickable(substring: String, click: (View) -> Unit)
        = enkompass(substring, ClickSpan(click))
