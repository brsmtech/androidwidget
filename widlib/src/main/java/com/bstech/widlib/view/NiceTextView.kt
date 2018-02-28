package com.bstech.widlib.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bstech.widlib.R

/**
 * Created by brayskiy on 2/22/18.
 */

class NiceTextView : RelativeLayout {

    private lateinit var niceText: String
    private lateinit var niceImage: ImageView

    private val textStrokePaint = Paint()
    private val textPaint = Paint()

    private var textSize: Float = 0.toFloat()
    private var textPadding: Float = 0.toFloat()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            init(context, attrs, defStyleAttr)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(this.context).inflate(R.layout.layout_nice_text_view, this, true)

        niceImage = findViewById(R.id.nice_text_view_image)

        if (attrs != null) {
            val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NiceTextView)

            val background = typedArray.getDrawable(R.styleable.NiceTextView_backgroundColor)
            if (background != null) {
                setBackgroundDrawable(background)
            } else {
                setBackgroundColor(typedArray.getColor(R.styleable.NiceTextView_backgroundColor, Color.TRANSPARENT))
            }

            textSize = typedArray.getDimensionPixelSize(R.styleable.NiceTextView_android_textSize, 10).toFloat()
            val textColor = typedArray.getColor(R.styleable.NiceTextView_android_textColor, Color.BLACK)
            textPadding = typedArray.getDimension(R.styleable.NiceTextView_textPadding, 8f)
            setPadding(textPadding.toInt(), textPadding.toInt(), textPadding.toInt(), textPadding.toInt())

            textPaint.isAntiAlias = true
            textPaint.color = textColor
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.textSize = textSize
            textPaint.style = Paint.Style.FILL
            textPaint.typeface = Typeface.DEFAULT_BOLD

            val textStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.NiceTextView_textStrokeWidth, 2).toFloat()
            val textStrokeColor = typedArray.getColor(R.styleable.NiceTextView_textStrokeColor, Color.BLACK)

            textStrokePaint.isAntiAlias = true
            textStrokePaint.color = textStrokeColor
            textStrokePaint.textAlign = Paint.Align.CENTER
            textStrokePaint.textSize = textSize
            textStrokePaint.typeface = Typeface.DEFAULT_BOLD
            textStrokePaint.style = Paint.Style.STROKE
            textStrokePaint.strokeWidth = textStrokeWidth

            niceText = typedArray.getString(R.styleable.NiceTextView_android_text)
            setText()

            typedArray.recycle()
        }
    }

    fun setText(textId: Int) {
        setText(resources.getString(textId))
    }

    fun setText(text: String) {
        niceText = text
        setText()
    }

    private fun setText() {
        val textBounds = Rect()
        textPaint.getTextBounds(niceText, 0, niceText.length, textBounds)

        val paddingX = textPadding.toInt()
        val paddingY = textPadding.toInt()

        val bitmap = Bitmap.createBitmap(
                textBounds.width() + paddingX, textBounds.height() + paddingY,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val textX = textBounds.width() / 2 + paddingX / 2
        val textY = height + textBounds.height();
        canvas.drawText(niceText, textX.toFloat(), textY.toFloat(), textStrokePaint)
        canvas.drawText(niceText, textX.toFloat(), textY.toFloat(), textPaint)

        niceImage.setImageBitmap(bitmap)
    }

    fun setBackgroundDrawable(color: Int) {
        // TODO
    }

    fun setTextStrokeColor(color: Int) {
        textStrokePaint.color = color
        setText()
    }

    fun setTextStrokeWidth(color: Int) {
        textStrokePaint.strokeWidth = color.toFloat()
        setText()
    }

    fun setTextColor(color: Int) {
        textPaint.color = color
        setText()
    }

    fun setTextSize(size: Int) {
        textPaint.textSize = size.toFloat()
        textStrokePaint.textSize = size.toFloat()
        setText()
    }
}
