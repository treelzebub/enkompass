package net.treelzebub.enkompass

import android.text.SpannableStringBuilder


/**
 * Created by Tre Murillo on 4/5/17
 */

/**
 * You'll want to call this on your String first before using the extensions in _Enkompass.kt.
 */
fun String.toSpannable() = SpannableStringBuilder(this)

/**
 * Feed it a substring and get the range of indices in the outer string. Because Android
 *
 * @param substring  a substring contained in the extended CharSequence. Will explode if
 *                   substring does not exist.
 */
internal fun CharSequence.which(substring: String): IntRange {
    return toString().let {
        val start = it.indexOf(substring)
        val end = start + substring.length
        start..end
    }
}
