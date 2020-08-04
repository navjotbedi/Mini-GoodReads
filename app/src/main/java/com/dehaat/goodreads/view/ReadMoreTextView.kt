package com.dehaat.goodreads.view

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView

class ReadMoreTextView : AppCompatTextView {

    private var originalText: CharSequence? = null

    constructor(context: Context) : super(context) {
        initUI()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initUI()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initUI()
    }


    private fun initUI() {
        originalText = text
        addReadMoreTo()
    }

    private fun addReadMoreTo() {
        val textLength = 2
        val moreLabel = "More"
        val moreLabelColor: Int = Color.BLUE

        setLines(textLength)
        text = originalText

        post(Runnable {
            var textLengthNew = textLength

            if (layout.lineCount <= textLength) {
                text = originalText
                return@Runnable
            }
            val lp = layoutParams as ViewGroup.MarginLayoutParams
            val subString = text.toString().substring(layout.getLineStart(0), layout.getLineEnd(textLength - 1))
            textLengthNew = subString.length - (moreLabel.length + 4 + lp.rightMargin / 6)

            val spannableStringBuilder = SpannableStringBuilder(text.subSequence(0, textLengthNew)).append("...").append(moreLabel)
            val ss = SpannableString.valueOf(spannableStringBuilder)
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    maxLines = Int.MAX_VALUE
                    text = originalText
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = moreLabelColor
                }
            }
            ss.setSpan(clickableSpan, ss.length - moreLabel.length, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val layoutTransition = LayoutTransition()
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            (parent as ViewGroup).layoutTransition = layoutTransition

            text = ss
            movementMethod = LinkMovementMethod.getInstance()
        })
    }

}