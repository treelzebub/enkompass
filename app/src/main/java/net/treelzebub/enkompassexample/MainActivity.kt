package net.treelzebub.enkompassexample

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import net.treelzebub.enkompass.*
import net.treelzebub.enkompass.spans.ClickSpan

/**
 * Created by Tre Murillo on 4/6/17
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        builderSpans()
//        varargSpans()
//        kbuilderSpans()
    }

    // Span various parts of a single String in one go.
    private fun builderSpans() {
        val body = "My text in bold and italics and with clickable words."
        val span = body.toSpannable()
                       .clickable("with clickable words") {
                           // do a thing when the user taps on this phrase.
                       }
                       .bold("bold")
                       .italic("italics")
                       .monospace("and stuff")
                       .build()
        val textView = findViewById(R.id.textview) as TextView
        textView.text = span
    }

    // This one spans the entire text, but you can do it with a substring, too.
    private fun varargSpans() {
        val body = "One Bold Link."
        body.enkompassAll(
                    ForegroundColorSpan(R.color.colorAccent),
                    StyleSpan(Typeface.BOLD),
                    ClickSpan {
                        view ->
                        // Sets an OnClickListener.
                        // Do stuff with the View, or startActivity(), or something else...
                    })
    }

    // TODO Styles a substring via kbuilder syntax
    private fun kbuilderSpans() {
//        val body = getString(R.string.spans_set_with_a_kbuilder))
//        val span = body.enkompass(R.string.substring_kbuilder) {
//            typeface = Typeface.Bold
//            color = R.color.orange
//            onClick = {
//                // do some shit
//            }
//            textView.text = span
//        }
    }
}