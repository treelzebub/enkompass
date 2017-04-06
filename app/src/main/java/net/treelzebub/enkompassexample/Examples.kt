package net.treelzebub.enkompassexample

import android.app.Activity
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import net.treelzebub.enkompass.*
import net.treelzebub.enkompass.spans.ClickSpan

/**
 * Created by Tre Murillo on 4/6/17
 */
object Examples {

    // Span various parts of a single String in one go.
    fun builderPattern(activity: Activity): CharSequence {
        val body = "My text in bold and italics and with clickable words."
        val span = body.toSpannable()
                       .clickable("with clickable words") {
                           // do a thing when the user taps on this phrase.
                       }
                       .bold("bold")
                       .italic("italics")
                       .monospace("and stuff")
                       .build()
        val textView = activity.findViewById(R.id.some_text_view) as TextView
        textView.text = span
    }

    // This one spans the entire text, but you can do it with a substring, too.
    fun varargSpans() {
        val body = "One Bold Link."
        body.toSpannable()
            .enkompassAll(
                ForegroundColorSpan(R.color.colorAccent),
                StyleSpan(Typeface.BOLD),
                ClickSpan {
                    view ->
                    // Sets an OnClickListener.
                    // Do stuff with the View, or startActivity(), or something else...
                })
            .build()
    }

    //    // TODO Styles a substring via kbuilder syntax
    //    fun styleSomeShit() {
    //        val body = getString(R.string.spans_set_with_a_kbuilder))
    //        val span = body.enkompass(R.string.substring_kbuilder) {
    //                            style = R.style.MyStyle
    //                            typeface = Typeface.Bold
    //                            color = R.color.orange
    //                            onClick = { // do some shit }
    //                        }
    //        textView.setText(span)
    //    }
}
