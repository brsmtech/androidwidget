package com.bstech.widlib.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.bstech.widlib.R
import com.bstech.widlib.util.FontManager

/**
 * Created by brayskiy on 9/28/16.
 */

class FontTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            init(attrs, defStyleAttr)
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