package net.treelzebub.enkompass

import android.text.SpannableStringBuilder


/**
 * Created by Tre Murillo on 4/5/17
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
        val end = start + substring.length
        start..end
    }
}

fun String.toSpannable() = SpannableStringBuilder(this)
