package net.treelzebub.enkompass

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.StyleRes
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView

/**
 * Created by Tre Murillo on 2/3/18
 */

/**
 * If you really want a more Java-style syntax, here ya go.
 *
 * Example:
 *     "Enkompass me, bruh!".enkompass("me", StyleSpan(Typeface.BOLD))
 */


fun String.enkompass(substring: String, vararg spans: Any) = toSpannable().enkompass(substring, *spans)
fun SpannableStringBuilder.enkompass(substring: String, vararg spans: Any) = apply {
    if (substring !in this) throw IllegalArgumentException("Substring not contained in the given String.")
    val range = which(substring)
    spans.forEach {
        setSpan(it, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
}


/**
 * Kotlin-style builder for applying various spans to one substring.
 *
 * Example:
 *     "Typesafe builders are neat!".enkompass("neat!") {
 *         bold()
 *         italic()
 *         clickable(textview) { foo() }
 *         colorize(getColor(R.color.light_urple)
 *     }
 */
fun String.enkompass(substring: String, enkompass: Enkompass.() -> Unit): SpannableStringBuilder {
    return Enkompass(this, substring).apply(enkompass).result
}


/**
 * This is not intended to be used directly.
 */
@Deprecated("I made you extension functions!")
class Enkompass(string: String, private val substring: String) {

    var result: SpannableStringBuilder = string.toSpannable()
        private set

    fun bold() = result.enkompass(substring, StyleSpan(Typeface.BOLD))

    fun italics() = result.enkompass(substring, StyleSpan(Typeface.ITALIC))

    fun boldItalics() = result.enkompass(substring, StyleSpan(Typeface.BOLD_ITALIC))

    fun monospace() = result.enkompass(substring, TypefaceSpan("monospace"))

    fun withStyle(context: Context, @StyleRes style: Int)
            = result.enkompass(substring, TextAppearanceSpan(context, style))

    fun colorize(resolvedColor: Int) = result.enkompass(substring, ForegroundColorSpan(resolvedColor))

    fun clickable(
            textview: TextView,
            movementMethod: LinkMovementMethod? = null,
            click: (View) -> Unit)
    {
        textview.movementMethod = movementMethod ?: LinkMovementMethod()
        result.enkompass(substring, object : ClickableSpan() {
            override fun onClick(widget: View) = click(widget)
        })
    }
}


private fun String.toSpannable() = SpannableStringBuilder(this)

/**
 * Feed it a substring and get the range of indices in the outer string. Enkompass uses
 * [Spanned.SPAN_INCLUSIVE_INCLUSIVE] under the hood.
 *
 * @param substring  a substring contained in the extended CharSequence. Will explode if
 *                   substring does not exist.
 */
private fun CharSequence.which(substring: String): IntRange {
    val start = indexOf(substring)
    val end = start + substring.length
    return start..end
}
