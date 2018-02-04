package net.treelzebub.enkompass

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.support.annotation.StyleRes
import android.support.annotation.VisibleForTesting
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
 * @param String (the extended class) The complete string, some or all of which will have spannables applied.
 * @param substring The part of the String you want to apply spannables to.
 * @param spans A vararg array of one or more spannable objects.
 *
 *
 * Example:
 *     "Enkompass me, bruh!".enkompass("me", StyleSpan(Typeface.BOLD))
 */
fun SpannableStringBuilder.enkompass(substring: String, vararg spans: Any) = apply {
    if (substring !in this) {
        throw IllegalArgumentException("Substring not contained in the given String.")
    }
    val range = which(substring)
    spans.forEach {
        setSpan(it, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

/**
 * String convenience, so you don't need to create your own [SpannableStringBuilder].
 */
@SuppressLint("VisibleForTests")
fun String.enkompass(substring: String, vararg spans: Any)
        = toSpannable().enkompass(substring, *spans)


/**
 * Kotlin-style builder for applying various spans to one substring.
 * Example:
 *     "Woohoo!".enkompass("hoo!") {
 *         boldItalics()
 *         clickable(textview) { foo() }
 *         colorize(getColor(R.color.light_urple))
 *     }
 *
 * @param String (the extended class) The complete string, some or all of which will have s
 *                pannables applied.
 * @param substring The part of the String you want to apply spannables to.
 * @param enkompass Any function of the [Enkompass] class. This lambda applies spannables to
 *                  the [substring].
 *
 * @return The resulting [SpannableStringBuilder], with the applied spannables.
 */
fun String.enkompass(substring: String, enkompass: Enkompass.() -> Unit): SpannableStringBuilder {
    return Enkompass(this, substring).apply(enkompass)
}


/**
 * This is not intended to be used directly.
 */
@Deprecated("I made you extension functions!")
class Enkompass(string: String, private val substring: String) : SpannableStringBuilder(string) {

    fun bold() = apply { enkompass(substring, StyleSpan(Typeface.BOLD)) }

    fun italics() = apply { enkompass(substring, StyleSpan(Typeface.ITALIC)) }

    fun boldItalics() = apply { enkompass(substring, StyleSpan(Typeface.BOLD_ITALIC)) }

    fun monospace() = apply { enkompass(substring, TypefaceSpan("monospace")) }

    fun style(context: Context, @StyleRes style: Int)
            = apply { enkompass(substring, TextAppearanceSpan(context, style)) }

    fun colorize(resolvedColor: Int)
            = apply { enkompass(substring, ForegroundColorSpan(resolvedColor)) }

    fun clickable(
            textview: TextView,
            movementMethod: LinkMovementMethod? = null,
            click: (View) -> Unit)
    = apply {
        textview.movementMethod = movementMethod ?: LinkMovementMethod()
        enkompass(substring, object : ClickableSpan() {
            override fun onClick(widget: View) = click(widget)
        })
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun String.toSpannable() = SpannableStringBuilder(this)

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun CharSequence.which(substring: String): IntRange {
    val start = indexOf(substring)
    val end = start + substring.length
    return start..end
}
