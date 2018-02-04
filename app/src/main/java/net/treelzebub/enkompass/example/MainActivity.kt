package net.treelzebub.enkompass.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.treelzebub.enkompass.enkompass

/**
 * Created by Tre Murillo on 4/6/17
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val string = "Hey this is neat!"
        val substring = "neat!"
        textview.text = string.enkompass(substring) {
            bold()
            italics()
            clickable(textview) {
                Toast.makeText(this@MainActivity, "Boop!", Toast.LENGTH_SHORT).show()
            }
        }

        textview.text = string.enkompass(2..4) {
            monospace()
        }
    }
}
