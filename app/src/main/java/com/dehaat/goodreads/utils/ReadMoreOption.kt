package com.dehaat.goodreads.utils

import android.animation.LayoutTransition
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView

class ReadMoreOption {

    companion object {
        fun addReadMoreTo(textView: TextView, text: CharSequence) {
            val textLength = 2
            val moreLabel = "More"
            val moreLabelColor: Int = Color.BLUE

            textView.setLines(textLength)
            textView.text = text

            textView.post(Runnable {
                var textLengthNew = textLength

                if (textView.layout.lineCount <= textLength) {
                    textView.text = text
                    return@Runnable
                }
                val lp = textView.layoutParams as MarginLayoutParams
                val subString = text.toString().substring(textView.layout.getLineStart(0), textView.layout.getLineEnd(textLength - 1))
                textLengthNew = subString.length - (moreLabel.length + 4 + lp.rightMargin / 6)

                val spannableStringBuilder = SpannableStringBuilder(text.subSequence(0, textLengthNew)).append("...").append(moreLabel)
                val ss = SpannableString.valueOf(spannableStringBuilder)
                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        textView.maxLines = Int.MAX_VALUE
                        textView.text = text
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = moreLabelColor
                    }
                }
                ss.setSpan(clickableSpan, ss.length - moreLabel.length, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                val layoutTransition = LayoutTransition()
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                (textView.parent as ViewGroup).layoutTransition = layoutTransition

                textView.text = ss
                textView.movementMethod = LinkMovementMethod.getInstance()
            })
        }
    }
}