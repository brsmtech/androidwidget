package com.bstech.widlib.view

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.bstech.widlib.R
import com.bstech.widlib.util.Validation

/**
 * Created by brayskiy on 11/28/17.
 */

class EditTextView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var editTextListener: EditTextListener? = null

    private lateinit var editTextViewContainer: RelativeLayout
    private lateinit var editTextViewInput: EditText
    private lateinit var editTextViewClear: LinearLayout
    private lateinit var editTextViewClearImage: ImageView
    private lateinit var editTextViewMessageContainer: LinearLayout
    private lateinit var editTextViewMessageSymbol: TextView
    private lateinit var editTextViewMessage: TextView

    private var defaultText: CharSequence? = null
    private var defaultMessage: CharSequence? = null
    private var clearButton = false
    private var position = 0

    private var validationAction: Validation.Action = Validation.Action.UNKNOWN
    var text: String
        get() = editTextViewInput.text.toString()
        set(text) = editTextViewInput.setText(text)
    var message: CharSequence?
        get() = editTextViewMessage.text
        set(message) = if (message != null) {
            editTextViewMessageContainer.visibility = View.VISIBLE
            editTextViewMessage.text = message
        } else {
            if (defaultMessage != null) {
                editTextViewMessageContainer.visibility = View.VISIBLE
                editTextViewMessage.text = defaultMessage
            } else {
                editTextViewMessageContainer.visibility = View.INVISIBLE
            }
        }

    interface EditTextListener {
        fun run()
    }

    init {
        if (!isInEditMode) {
            init(context, attrs, defStyleAttr)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(this.context).inflate(R.layout.layout_edit_text_view, this, true)

        editTextViewContainer = findViewById(R.id.edit_text_view_container)
        editTextViewInput = findViewById(R.id.edit_text_view_input)
        editTextViewMessageContainer = findViewById(R.id.edit_text_view_message_container)
        editTextViewMessageSymbol = findViewById(R.id.edit_text_view_message_symbol)
        editTextViewMessage = findViewById(R.id.edit_text_view_message)
        editTextViewClear = findViewById(R.id.edit_text_view_clear)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextView)

            clearButton = typedArray.getBoolean(R.styleable.EditTextView_clear, false)

            if (clearButton) {
                editTextViewClear.setOnClickListener { _ ->
                    editTextViewInput.setText("")
                    clearMessage()
                }
                editTextViewClearImage = editTextViewClear.findViewById(R.id.edit_text_view_clear_image)
                setImageIcon(editTextViewClearImage, R.color.white)
            }

            defaultText = typedArray.getString(R.styleable.EditTextView_android_text)
            if (defaultText != null) {
                editTextViewInput.setText(defaultText)
            }

            editTextViewClear.visibility = if (clearButton && defaultText != null) View.VISIBLE else View.GONE

            val textColor = typedArray.getColor(R.styleable.EditTextView_android_textColor, Color.WHITE)
            editTextViewInput.setTextColor(textColor)

            val maxLength = typedArray.getInt(R.styleable.EditTextView_maxLength, 256)
            editTextViewInput.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

            val cursorDrawable = typedArray.getDrawable(R.styleable.EditTextView_cursorColor)
            try {
                val field = EditText::class.java.getDeclaredField("mCursorDrawableRes")
                field.setAccessible(true)
                field.set(editTextViewInput, cursorDrawable)
            } catch (ignored: Exception) {
            }

            val backgroundDrawable = typedArray.getDrawable(R.styleable.EditTextView_backgroundColor)
            if (backgroundDrawable != null) {
                editTextViewContainer.background = backgroundDrawable
            } else {
                editTextViewContainer.setBackgroundColor(typedArray.getColor(
                        R.styleable.EditTextView_backgroundColor, Color.WHITE))
            }

            defaultMessage = typedArray.getString(R.styleable.EditTextView_message)
            if (editTextViewInput.text.length > 0) {
                message = defaultMessage
            }

            val hint = typedArray.getString(R.styleable.EditTextView_android_hint)
            if (hint != null) {
                setHint(hint)

                val hintColor = typedArray.getColor(R.styleable.EditTextView_android_textColorHint, Color.WHITE)
                editTextViewInput.setHintTextColor(hintColor)
            }

            val inputType = typedArray.getInt(R.styleable.EditTextView_android_inputType, EditorInfo.TYPE_NULL)
            setInputType(inputType)

            val imeOptions = typedArray.getInt(R.styleable.EditTextView_android_imeOptions, EditorInfo.IME_NULL)
            setImeOptions(imeOptions)

            val messageColor = typedArray.getColor(R.styleable.EditTextView_messageColor, Color.WHITE)
            editTextViewMessage.setTextColor(messageColor)
            editTextViewMessageSymbol.setTextColor(messageColor)

            typedArray.recycle()
        }

        editTextViewInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                position = charSequence.length
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                clearMessage()

                if (clearButton) {
                    editTextViewClear.visibility = if (editable.toString().length > 0) View.VISIBLE else View.INVISIBLE
                }

                if (editTextListener != null) {
                    editTextListener!!.run()
                }

                if (!validate()) {
                    message = defaultMessage
                } else {
                    clearMessage()
                }
            }
        })
    }

    fun setValidation(validation: Validation.Action) { validationAction = validation }
    fun setOnEditTextListener(listener: EditTextListener) { editTextListener = listener }
    fun setInputType(inputType: Int) { editTextViewInput.inputType = inputType }
    fun setImeOptions(imeOptions: Int) { editTextViewInput.imeOptions = imeOptions }
    fun setHint(hint: CharSequence) { editTextViewInput.hint = hint }
    fun setHint(hint: Int) { editTextViewInput.setHint(hint) }
    fun length(): Int { return editTextViewInput.length() }
    fun setSelection(selection: Int) { editTextViewInput.setSelection(selection) }
    fun clearText() { editTextViewClear.performClick() }
    fun clearMessage() { editTextViewMessageContainer.visibility = View.INVISIBLE }
    fun setMessage(message: String) { editTextViewMessage.text = message }
    fun getMessage(): String { return editTextViewMessage.text.toString() }

    fun validate(): Boolean {
        val text = editTextViewInput.text.toString()
        return Validation.validate(text, validationAction) == Validation.Result.OK
    }

    fun setSelectionEnd() {
        editTextViewInput.setSelection(editTextViewInput.text.length)
    }

    private fun setImageIcon(image: ImageView?, colorId: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val drawable = DrawableCompat.wrap(image!!.drawable)
            image.setImageDrawable(drawable)
            drawable.mutate().setColorFilter(ContextCompat.getColor(context, colorId),
                    PorterDuff.Mode.SRC_IN)
        } else {
            DrawableCompat.setTint(image!!.drawable, ContextCompat.getColor(context, colorId))
        }
    }
}
