package com.bstech.widlib.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.bstech.widlib.R
import com.bstech.widlib.util.FontManager

/**
 * Created by brayskiy on 9/28/16.
 */

class FontTextView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : AppCompatTextView(context, attrs, defStyle) {

    init {
        if (!isInEditMode) {
            init(attrs, defStyle)
        }
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.FontTextView, defStyle, 0)
        val indicator = typedArray.getInt(R.styleable.FontTextView_fontName, DEFAULT_DIMENSION)
        typedArray.recycle()
        typeface = FontManager.get().customFontTypeface(indicator)
    }

    companion object {
        val TYPEFACE_ICON_FONTS = 1
        private val DEFAULT_DIMENSION = 0
    }
}