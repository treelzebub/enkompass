package net.treelzebub.enkompass

import android.util.Log

/**
 * Created by Tre Murillo on 4/5/17
 */

internal inline fun <reified T> T.log(msg: String, ex: Exception? = null, error: Boolean = false) {
    val tag = T::class.java.simpleName
    if (error) {
        Log.e(tag, msg, ex)
    } else {
        Log.d(tag, msg, ex)
    }
}