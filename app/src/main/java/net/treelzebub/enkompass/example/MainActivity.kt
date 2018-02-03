package net.treelzebub.enkompass.example

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.treelzebub.enkompass.*
import net.treelzebub.enkompass.spans.ClickSpan

/**
 * Created by Tre Murillo on 4/6/17
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        regularAssSpans()
        builderSpans()
//        varargSpans()
//        kbuilderSpans()
    }

    private fun regularAssSpans() {
        val spannable = SpannableStringBuilder("test bold")
//        spannable.setSpan(StyleSpan(Typeface.BOLD), 5, 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
//        textview.text = spannable
        textview.text = spannable.enkompass("bold", StyleSpan(Typeface.BOLD))
    }

    // Span various parts of a single String in one go.
    private fun builderSpans() {
        val body = "My text in bold and italics and with clickable words and stuff."
        val span = body.toSpannable()
                       .clickable("with clickable words") {
                           // do a thing when the user taps on this phrase.
                       }
                       .bold("bold")
                       .italic("italics")
                       .monospace("and stuff")
                       .build()
        textview.text = span
    }

    // This one spans the entire text, but you can do it with a substring, too.
    private fun varargSpans() {
        val body = "One Bold Link."
        body.enkompassAll(
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)),
                    StyleSpan(Typeface.BOLD),
                    ClickSpan { _->
                        Toast.makeText(this, "Woo!", Toast.LENGTH_SHORT).show()
                    }
        )
        textview.text = body
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