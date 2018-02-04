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
 * This function is where the actual work of the library happens. Feed it an
 *
 * @param[String] [the extended class] The complete string, some or all of which will have spannables applied.
 * @param[intRange] The start and end of the desired spans. Corresponds to [Spanned.SPAN_INCLUSIVE_INCLUSIVE].
 * @param[spans] A vararg array of one or more spannable objects.
 */
fun CharSequence.enkompass(strategy: Enkompass.Strategy, vararg spans: Any): Spannable {
    val substring = substring(strategy.range.let { it.first until it.last })
    if (substring !in this) {
        throw IllegalArgumentException("Substring not contained in the given String.")
    }

    val spannable = this as? Spannable ?: SpannableString(this)
    spans.forEach {
        spannable.setSpan(it, strategy.range.first, strategy.range.last, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
    return spannable
}


/**
 * Kotlin-style builder for applying various spans to one substring.
 * Example:
 *     "Woohoo!".enkompass("hoo!") {
 *         boldItalics()
 *         clickable(textview) { foo() }
 *         colorize(getColor(R.color.light_urple))
 *     }
 *
 * @param[String] (the extended class) The complete string, some or all of which will have s
 *                pannables applied.
 * @param[substring] The part of the String you want to apply spannables to.
 * @param[enkompass] Any function of the [Enkompass] class. This lambda applies spannables to
 *                  the [substring].
 *
 * @return The resulting [SpannableStringBuilder], with the applied spannables.
 */
fun String.enkompass(substring: String, enkompass: Enkompass.() -> Unit)
        = Enkompass(this, Enkompass.SubstringStrategy(this, substring)).apply(enkompass)

fun String.enkompass(intRange: IntRange, enkompass: Enkompass.() -> Unit)
        = Enkompass(this, Enkompass.RangeStrategy(intRange)).apply(enkompass)


/**
 * This is not intended to be used directly.
 */
@Deprecated("I made you extension functions!")
class Enkompass(string: String, private val strategy: Strategy) : SpannableString(string) {

    interface Strategy {
        val range: IntRange
    }

    class SubstringStrategy(string: String, substring: String) : Strategy {
        @SuppressLint("VisibleForTests")
        override val range = string.which(substring)
    }

    class RangeStrategy(override val range: IntRange) : Strategy


    fun bold() = apply { enkompass(strategy, StyleSpan(Typeface.BOLD)) }

    fun italics() = apply { enkompass(strategy, StyleSpan(Typeface.ITALIC)) }

    fun boldItalics() = apply { enkompass(strategy, StyleSpan(Typeface.BOLD_ITALIC)) }

    fun monospace() = apply { enkompass(strategy, TypefaceSpan("monospace")) }

    fun style(context: Context, @StyleRes style: Int)
            = apply { enkompass(strategy, TextAppearanceSpan(context, style)) }

    fun colorize(resolvedColor: Int)
            = apply { enkompass(strategy, ForegroundColorSpan(resolvedColor)) }

    fun clickable(
            textview: TextView,
            movementMethod: LinkMovementMethod? = null,
            click: (View) -> Unit
    ) = apply {
        textview.movementMethod = movementMethod ?: LinkMovementMethod()
        enkompass(strategy, object : ClickableSpan() {
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
