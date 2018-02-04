package net.treelzebub.enkompass

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.support.annotation.StyleRes
import android.support.annotation.VisibleForTesting
import android.text.Spannable
import android.text.SpannableString
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
 * This is where the actual
 *
 * @param String (the extended class) The complete string, some or all of which will have spannables applied.
 * @param substring The part of the String you want to apply spannables to.
 * @param spans A vararg array of one or more spannable objects.
 */
fun CharSequence.enkompass(
        substring: String,
        vararg spans: Any,
        strategy: Enkompass.Strategy = Enkompass.SubstringStrategy(this.toString())
) = apply {
    if (substring !in this) {
        throw IllegalArgumentException("Substring not contained in the given String.")
    }

    val spannable = this as? Spannable ?: SpannableString(this)
    val range = strategy.getRange(substring)
    spans.forEach {
        spannable.setSpan(it, range.first, range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

fun CharSequence.enkompass(
        intRange: IntRange,
        vararg spans: Any,
        strategy: Enkompass.Strategy = Enkompass.RangeStrategy(intRange)
) = apply { enkompass(substring(intRange), spans, strategy) }

/**
 * If your substring occurs more than once in the outer string, you'll want to use this signature,
 * which takes an [IntRange] that corresponds to [Spanned.SPAN_INCLUSIVE_INCLUSIVE].
 */
@SuppressLint("VisibleForTests")
fun String.enkompass(
        intRange: IntRange,
        strategy: Enkompass.Strategy = Enkompass.RangeStrategy(intRange),
        vararg spans: Any
) = toSpannable().enkompass(intRange, strategy, spans)

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
fun String.enkompass(
        substring: String,
        strategy: Enkompass.Strategy = Enkompass.SubstringStrategy(this),
        enkompass: Enkompass.() -> Unit
) = Enkompass(this, substring, strategy).apply(enkompass)

fun String.enkompass(
        intRange: IntRange,
        strategy: Enkompass.Strategy = Enkompass.SubstringStrategy(this),
        enkompass: Enkompass.() -> Unit
) = Enkompass(this, intRange, strategy).apply(enkompass)




/**
 * This is not intended to be used directly.
 */
@Deprecated("I made you extension functions!")
class Enkompass(string: String, private val substring: String, val strategy: Strategy) : SpannableString(string) {

    interface Strategy {
        fun getRange(substring: String): IntRange
    }

    class SubstringStrategy(private val string: String) : Strategy {
        @SuppressLint("VisibleForTests")
        override fun getRange(substring: String): IntRange {
            return string.which(substring)
        }
    }

    class RangeStrategy(private val range: IntRange) : Strategy {
        override fun getRange(substring: String) = range
    }

    fun bold() = apply { enkompass(substring, strategy, StyleSpan(Typeface.BOLD)) }

    fun italics() = apply { enkompass(substring, strategy, StyleSpan(Typeface.ITALIC)) }

    fun boldItalics() = apply { enkompass(substring, strategy, StyleSpan(Typeface.BOLD_ITALIC)) }

    fun monospace() = apply { enkompass(substring, strategy, TypefaceSpan("monospace")) }

    fun style(context: Context, @StyleRes style: Int)
            = apply { enkompass(substring, strategy, TextAppearanceSpan(context, style)) }

    fun colorize(resolvedColor: Int)
            = apply { enkompass(substring, strategy, ForegroundColorSpan(resolvedColor)) }

    fun clickable(
            textview: TextView,
            movementMethod: LinkMovementMethod? = null,
            strategy: Strategy = SubstringStrategy(this.toString()),
            click: (View) -> Unit
    ) = apply {
        textview.movementMethod = movementMethod ?: LinkMovementMethod()
        enkompass(substring, strategy, object : ClickableSpan() {
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
