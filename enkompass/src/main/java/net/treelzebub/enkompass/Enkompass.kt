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
 * For Java-style builders, use this.
 */
fun SpannableStringBuilder.enkompass(substring: String, vararg spans: Any) = apply {
    if (substring !in this) throw IllegalArgumentException("Substring not contained in the given String.")
    val range = this.which(substring)
    spans.forEach {
        log("${range.first} to ${range.last}")
        setSpan(it, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
}


/**
 * For Kotlin-style builders, user this.
 */
fun String.enkompass(substring: String, enkompass: Enkompass.() -> Unit): SpannableStringBuilder {
    return Enkompass(this, substring).apply(enkompass).result
}


/**
 * This is not intended to be used directly.
 */
class Enkompass(string: String, private val substring: String) {

    init {
        log("new Enkompass")
    }

    var result: SpannableStringBuilder = string.toSpannable()
        private set

    private val spannable = string.toSpannable()
    private val which = string.which(substring)

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
