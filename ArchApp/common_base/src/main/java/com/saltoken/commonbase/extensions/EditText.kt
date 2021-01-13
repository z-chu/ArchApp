package com.saltoken.commonbase.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.IntRange


/** Listens for an EditText's text changes and sends them into a callback. */
fun EditText.doOnTextChanged(
    @IntRange(from = 0, to = 10000) debounce: Long = 0,
    action: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        var callbackRunner: OnTextChangedRunnable? = null

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            if (debounce <= 0) {
                action.invoke(s,start,before,count)
            } else {
                callbackRunner?.let {
                    removeCallbacks(it)
                }
                if (callbackRunner == null) {
                    callbackRunner = OnTextChangedRunnable(s, start, before, count, action)
                } else {
                    callbackRunner!!.also {
                        it.text = s
                        it.start = start
                        it.before = start
                        it.count = start
                    }
                }
                postDelayed(callbackRunner, debounce)
            }
        }
    })
}

private class OnTextChangedRunnable(
    var text: CharSequence?,
    var start: Int,
    var before: Int,
    var count: Int,
    val action: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit
) : Runnable {
    override fun run() {
        action.invoke(text, start, before, count)
    }

}
