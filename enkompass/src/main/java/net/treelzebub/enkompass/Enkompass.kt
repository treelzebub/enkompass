package net.treelzebub.enkompass

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.StyleRes
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView

/**
 * Created by Tre Murillo on 2/3/18
 *
 * This is not intended to be used directly.
 */
//@Deprecated("I made you extension functions!")
class Enkompass(string: String, private val strategy: Strategy) : SpannableString(string) {

    interface Strategy {
        val range: IntRange
    }

    class SubstringStrategy(string: String, substring: String) : Strategy {
        override val range = string.which(substring)
    }

    class RangeStrategy(override val range: IntRange) : Strategy


    fun bold()
            = enkompass(strategy, StyleSpan(Typeface.BOLD))

    fun italics()
            = enkompass(strategy, StyleSpan(Typeface.ITALIC))

    fun boldItalics()
            = enkompass(strategy, StyleSpan(Typeface.BOLD_ITALIC))

    fun underline()
            = enkompass(strategy, UnderlineSpan())

    fun monospace()
            = enkompass(strategy, TypefaceSpan("monospace"))

    fun superscript()
            = enkompass(strategy, SuperscriptSpan())

    fun subscript()
            = enkompass(strategy, SubscriptSpan())

    fun style(context: Context, @StyleRes style: Int)
            = enkompass(strategy, TextAppearanceSpan(context, style))

    fun foregroundColor(resolvedColor: Int)
            = enkompass(strategy, ForegroundColorSpan(resolvedColor))

    fun backgroundColor(resolvedColor: Int)
            = enkompass(strategy, BackgroundColorSpan(resolvedColor))

    fun size(proportion: Float)
            = enkompass(strategy, RelativeSizeSpan(proportion))

    fun clickable(
            textview: TextView,
            movementMethod: MovementMethod? = LinkMovementMethod.getInstance(),
            click: (View) -> Unit
    ): Spannable {
        textview.movementMethod = movementMethod
        return enkompass(strategy, object : ClickableSpan() {
            override fun onClick(widget: View) = click(widget)
        })
    }

    fun image(drawable: Drawable, alignBaseline: Boolean): Spannable {
        val alignment = if (alignBaseline) ImageSpan.ALIGN_BASELINE else ImageSpan.ALIGN_BOTTOM
        return enkompass(strategy, ImageSpan(drawable, alignment))
    }

    fun url(url: String)
            = enkompass(strategy, URLSpan(url))

    fun custom(spans: () -> Any) = enkompass(strategy, spans())
}
