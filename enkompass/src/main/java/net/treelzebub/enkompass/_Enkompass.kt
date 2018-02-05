package net.treelzebub.enkompass

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned


/**
 * Kotlin-style builder for applying various spans to one substring.
 *
 * @param[String] (the extended class) The complete string, some or all of which will have
 *                 spannables applied.
 * @param[substring] The part of the String you want to apply spannables to.
 * @param[enkompass] Any function of the [Enkompass] class. This lambda applies spannables to
 *                   the [substring], and enables the snazzy DSL.
 *
 * @return The resulting [SpannableStringBuilder], with the applied spannables.
 *
 * @sample[enkompass]
 * "Woohoo!".enkompass("hoo!") {
 *     boldItalics()
 *     clickable(textview) { foo() }
 *     foregroundColor(getColor(R.color.light_urple))
 * }
 */
fun String.enkompass(substring: String, enkompass: Enkompass.() -> Unit)
        = Enkompass(this, Enkompass.SubstringStrategy(this, substring)).apply(enkompass)

/**
 * Kotlin-style builder for applying various spans to a range of characters.
 *
 * For example, this will make "012" bold:
 *      "012345".enkompass(0 until 3) { bold() }
 *
 * @param[String] (the extended class) The complete string, some or all of which will have
 *                 spannables applied.
 * @param[intRange] The range of indices in the outer string that you want spanned. Use of Kotlin's
 *                  `until` is recommended. The last index will not have a span applied.
 *                  `0 until 3` will span the first 3 indices: 0, 1, and 2.
 * @param[enkompass] Any function of the [Enkompass] class. This lambda applies spannables to
 *                   the [substring], and enables the snazzy DSL.
 */
fun String.enkompass(intRange: IntRange, enkompass: Enkompass.() -> Unit)
        = Enkompass(this, Enkompass.RangeStrategy(intRange)).apply(enkompass)

internal fun CharSequence.which(substring: String): IntRange {
    val start = indexOf(substring)
    val end = start + substring.length
    return start until end
}

internal fun CharSequence.enkompass(strategy: Enkompass.Strategy, vararg spans: Any): Spannable {
    val substring = substring(strategy.range)
    if (substring !in this) {
        throw IllegalArgumentException("Substring not contained in the given String.")
    }

    val spannable = this as? Spannable ?: SpannableString(this)
    spans.forEach {
        spannable.setSpan(it, strategy.range.first, strategy.range.last + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
    return spannable
}