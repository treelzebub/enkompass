package net.treelzebub.enkompass

import android.content.Context
import android.support.annotation.StringRes
import android.text.SpannableStringBuilder

/**
 * Created by Tre Murillo on 4/5/17
 */


/**
 * Set one or many Spans on a substring contained in the extended String.
 * Will explode if substring does not exist.
 *
 * @return  a [SpannableStringBuilder] that can be passed to TextView.setText()
 */
fun String.enkompass(substring: String, vararg spans: Any) = toSpannable().enkompass(substring, *spans)

/**
 * TODO setSpan's param `flags` is undocumented. fun.
 */
fun SpannableStringBuilder.enkompass(substring: String, vararg spans: Any) = apply {
    if (substring !in this) throw IllegalArgumentException("Substring not contained in the given String.")
    val range = this.which(substring)
    spans.forEach {
        setSpan(it, range.first, range.last, 0) // last param is flags. see TODO
    }
}

/**
 * Convenience for the above, with a String resource.
 */
fun SpannableStringBuilder.enkompass(c: Context, @StringRes substring: Int, vararg spans: Any) = apply {
    enkompass(c.getString(substring), *spans)
}

/**
 * Convenience for the above, but it spans the whole String.
 */
fun SpannableStringBuilder.enkompassAll(vararg spans: Any) = apply {
    enkompass(toString(), *spans)
}

/**
 * I provide this because Android has given us a construct that appears to be a Builder Pattern,
 * but is nothing of the sort. That's what Enkompass is all about. Obviously, this call is optional,
 * but I find it to be useful to the reader: a clear signal that this is a CharSequence that can be
 * passed to TextView.setText()
 *
 * TODO look into TextView.setSpannableFactory()
 */
fun SpannableStringBuilder.build(): CharSequence = this
