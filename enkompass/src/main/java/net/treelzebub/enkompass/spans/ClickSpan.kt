package net.treelzebub.enkompass.spans

import android.text.style.ClickableSpan
import android.view.View

/**
 * Created by Tre Murillo on 4/5/17
 *
 * Click on a View. Do a thing.
 */
class ClickSpan(private val click: (View) -> Unit) : ClickableSpan() {
    override fun onClick(widget: View) = click(widget)
}