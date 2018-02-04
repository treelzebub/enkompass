package net.treelzebub.enkompass.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.treelzebub.enkompass.enkompass

/**
 * Created by Tre Murillo on 4/6/17
 */
class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Call enkompass on the outer string, and feed it the substring that you'd like styled!
        val string = "Hey this is neat!"
        val substring = "neat!"
        textview_string.text = string.enkompass(substring) {
            bold()
            italics()
            clickable(textview_string) {
                Toast.makeText(this@MainActivity, "Boop!", Toast.LENGTH_SHORT).show()
            }
        }

        // "Wow" occurs twice, so we can use the IntRange signature to only style the first one.
        textview_intrange.text = "Wow Bob Wow.".enkompass(0 until 4) {
            bold()
            italics()
        }
    }
}
