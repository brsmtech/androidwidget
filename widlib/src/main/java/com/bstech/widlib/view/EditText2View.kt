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
import android.widget.*
import com.bstech.widlib.R
import com.bstech.widlib.util.Validation

/**
 * Created by brayskiy on 11/28/17.
 */

class EditText2View : LinearLayout {
    private var editTextListener: EditTextListener?= null

    private lateinit var editTextView1Container: RelativeLayout
    private lateinit var editTextView1Input: EditText
    private lateinit var editTextView1Clear: LinearLayout
    private lateinit var editTextView1ClearImage: ImageView

    private lateinit var editTextView2Container: RelativeLayout
    private lateinit var editTextView2Input: EditText
    private lateinit var editTextView2Clear: LinearLayout
    private lateinit var editTextView2ClearImage: ImageView

    private lateinit var editTextViewMessageContainer: LinearLayout
    private lateinit var editTextViewMessageSymbol: TextView
    private lateinit var editTextViewMessage: TextView

    private var clearButton = false

    private var position1 = 0
    private val position2 = 0

    private var errorMessage1: String? = null
    private var errorMessage2: String? = null

    private var validationAction: Validation.Action = Validation.Action.UNKNOWN

    var text1: String
        get() = editTextView1Input.text.toString()
        set(text) = editTextView1Input.setText(text)

    var text2: String
        get() = editTextView2Input.text.toString()
        set(text) = editTextView2Input.setText(text)

    val text: String?
        get() = if (validate()) editTextView1Input.text.toString() else null

    val message: CharSequence
        get() = editTextViewMessage.text

    interface EditTextListener {
        fun run()
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!isInEditMode) {
            init(context, attrs, defStyleAttr)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        orientation = LinearLayout.VERTICAL
        LayoutInflater.from(this.context).inflate(R.layout.layout_edit_text2_view, this, true)

        editTextViewMessageContainer = findViewById(R.id.edit_text2_view_message_container)
        editTextViewMessageSymbol = findViewById(R.id.edit_text2_view_message_symbol)
        editTextViewMessage = findViewById(R.id.edit_text2_view_message)

        editTextView1Container = findViewById(R.id.edit_text2_view1_container)
        editTextView1Input = editTextView1Container.findViewById(R.id.edit_text2_view1_input)
        editTextView1Clear = editTextView1Container.findViewById(R.id.edit_text2_view1_clear)

        editTextView2Container = findViewById(R.id.edit_text2_view2_container)
        editTextView2Input = editTextView2Container.findViewById(R.id.edit_text2_view2_input)
        editTextView2Clear = editTextView2Container.findViewById(R.id.edit_text2_view2_clear)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditText2View)

        if (attrs != null) {
            clearButton = typedArray.getBoolean(R.styleable.EditText2View_clear, false)

            val text1 = typedArray.getString(R.styleable.EditText2View_text1)
            if (text1 != null) {
                editTextView1Input.setText(text1)
            }

            val text2 = typedArray.getString(R.styleable.EditText2View_text2)
            if (text1 != null) {
                editTextView2Input.setText(text2)
            }

            editTextView1Clear.visibility = if (clearButton && text1 != null) View.VISIBLE else View.GONE
            editTextView2Clear.visibility = if (clearButton && text1 != null) View.VISIBLE else View.GONE

            if (clearButton) {
                editTextView1Clear.setOnClickListener { _ ->
                    clearMessage()
                    editTextView1Input.setText("")
                }
                editTextView1ClearImage = editTextView1Clear.findViewById(R.id.edit_text2_view1_clear_image)
                setImageIcon(editTextView1ClearImage, R.color.white)

                editTextView2Clear.setOnClickListener { _ ->
                    clearMessage()
                    editTextView2Input.setText("")
                }
                editTextView2ClearImage = editTextView2Clear.findViewById(R.id.edit_text2_view2_clear_image)
                setImageIcon(editTextView2ClearImage, R.color.white)
            }

            val textColor = typedArray.getColor(R.styleable.EditText2View_android_textColor, Color.WHITE)
            editTextView1Input.setTextColor(textColor)
            editTextView2Input.setTextColor(textColor)

            val maxLength = typedArray.getInt(R.styleable.EditText2View_maxLength, 256)
            val inputFilter = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
            editTextView1Input.filters = inputFilter
            editTextView2Input.filters = inputFilter

            val cursorDrawable = typedArray.getDrawable(R.styleable.EditText2View_cursorColor)
            try {
                val field = EditText::class.java.getDeclaredField("mCursorDrawableRes")
                field.isAccessible = true
                field.set(editTextView1Input, cursorDrawable)
                field.set(editTextView2Input, cursorDrawable)
            } catch (ignored: Exception) {
            }

            val backgroundDrawable = typedArray.getDrawable(R.styleable.EditText2View_backgroundColor)
            if (backgroundDrawable != null) {
                editTextView1Container.background = backgroundDrawable
                editTextView2Container.background = backgroundDrawable
            } else {
                editTextView1Container.setBackgroundColor(typedArray.getColor(R.styleable.EditText2View_backgroundColor, Color.WHITE))
                editTextView2Container.setBackgroundColor(typedArray.getColor(R.styleable.EditText2View_backgroundColor, Color.WHITE))
            }

            errorMessage1 = typedArray.getString(R.styleable.EditText2View_message1)
            errorMessage2 = typedArray.getString(R.styleable.EditText2View_message2)

            val hint1 = typedArray.getString(R.styleable.EditText2View_hint1)
            if (hint1 != null) {
                editTextView1Input.hint = hint1
            }

            val hint2 = typedArray.getString(R.styleable.EditText2View_hint2)
            if (hint2 != null) {
                editTextView2Input.hint = hint2
            }

            val hintColor = typedArray.getColor(R.styleable.EditText2View_hintColor, Color.WHITE)
            editTextView1Input.setHintTextColor(hintColor)
            editTextView2Input.setHintTextColor(hintColor)

            val actionIme1 = typedArray.getInt(R.styleable.EditText2View_ime1, 0)
            if (actionIme1 != 0) {
                editTextView1Input.imeOptions = actionIme1
            }

            val actionIme2 = typedArray.getInt(R.styleable.EditText2View_ime2, 0)
            if (actionIme2 != 0) {
                editTextView2Input.imeOptions = actionIme2
            }

            val messageColor = typedArray.getColor(R.styleable.EditText2View_messageColor, Color.WHITE)
            editTextViewMessage.setTextColor(messageColor)
            editTextViewMessageSymbol.setTextColor(messageColor)

            typedArray.recycle()
        }

        editTextView1Input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                position1 = charSequence.length
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                clearMessage()

                if (clearButton) {
                    editTextView1Clear.visibility = if (editable.isNotEmpty()) View.VISIBLE else View.INVISIBLE
                }

                validate()

                editTextListener?.run()
            }
        })

        editTextView2Input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                position1 = charSequence.length
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                clearMessage()

                if (clearButton) {
                    editTextView2Clear.visibility = if (editable.toString().length > 0) View.VISIBLE else View.INVISIBLE
                }

                editTextListener?.run()

                validate()
            }
        })
    }

    fun setHint1(hint: CharSequence) {
        editTextView1Input.hint = hint
    }

    fun setHint1(hint: Int) {
        editTextView1Input.setHint(hint)
    }

    fun setHint2(hint: CharSequence) {
        editTextView2Input.hint = hint
    }

    fun setHint2(hint: Int) {
        editTextView2Input.setHint(hint)
    }

    fun setValidation(validation: Validation.Action) {
        validationAction = validation
    }

    fun length1(): Int {
        return editTextView1Input.length()
    }

    fun length2(): Int {
        return editTextView2Input.length()
    }

    fun setSelection1(selection: Int) {
        editTextView1Input.setSelection(selection)
    }

    fun setSelection2(selection: Int) {
        editTextView2Input.setSelection(selection)
    }

    fun clearText1() {
        editTextView1Clear.performClick()
    }

    fun clearText2() {
        editTextView2Clear.performClick()
    }

    fun requestFocus1() {
        editTextView1Input.requestFocus()
    }

    fun requestFocus2() {
        editTextView2Input.requestFocus()
    }

    fun setMessage(message: String?) {
        editTextViewMessageContainer.visibility = View.VISIBLE
        editTextViewMessage.text = message
    }

    fun clearMessage() {
        editTextViewMessageContainer.visibility = View.INVISIBLE
    }

    fun setEditTextListener(listener: EditTextListener) {
        editTextListener = listener
    }

    fun validate(): Boolean {
        val text1 = editTextView1Input.text.toString()
        val text2 = editTextView2Input.text.toString()

        if (text1.isEmpty() && text2.isEmpty()) {
            clearMessage()
            return true
        } else if (Validation.validate(text1, validationAction) != Validation.Result.OK) {
            setMessage(errorMessage1)
        } else if (text1 != text2) {
            setMessage(errorMessage2)
        } else {
            clearMessage()
            return true
        }

        return false
    }

    fun setInputType(inputType: Int) {
        editTextView1Input.inputType = inputType
        editTextView2Input.inputType = inputType
    }

    fun setInputType(inputType1: Int, inputType2: Int) {
        editTextView1Input.inputType = inputType1
        editTextView2Input.inputType = inputType2
    }

    fun setImeOptions(imeOptions1: Int, imeOptions2: Int) {
        editTextView1Input.imeOptions = imeOptions1
        editTextView2Input.imeOptions = imeOptions2
    }

    fun setHint(hint1: CharSequence, hint2: CharSequence) {
        editTextView1Input.hint = hint1
        editTextView2Input.hint = hint2
    }

    fun setHint(hint1: Int, hint2: Int) {
        editTextView1Input.setHint(hint1)
        editTextView1Input.setHint(hint2)
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
