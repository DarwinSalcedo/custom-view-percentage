package com.visual.customs.custom

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText


class PercentageEditText : TextInputEditText, TextWatcher {
    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 100
        const val MAX_LENGTH = 4
    }

    private var current: String? = null
    private var symbol = "%"

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
        current = current ?: ""
        if (s.toString() != current) {
            val cleanString =
                s.toString().replace("[$,.]".toRegex(), "").replace(symbol.toRegex(), "")
                    .replace("\\s+".toRegex(), "")
            if (cleanString.isNotEmpty()) {
                val parsedInt = cleanString.toInt()
                if (parsedInt in MIN_VALUE..MAX_VALUE) {
                    val formatted = "$parsedInt$symbol"

                    current = formatted

                    setText(formatted)

                } else setText(current)

                if (current!!.isNotEmpty())
                    setSelection(current!!.length - 1)
            } else {
                current = ""
                setText(current)
            }

        }
    }

    override fun afterTextChanged(editable: Editable) {
    }


    @SuppressLint("CustomViewStyleable")
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inputType = InputType.TYPE_CLASS_NUMBER
        maxLines = 1
        filters = arrayOf(InputFilter.LengthFilter(MAX_LENGTH))
    }


    fun getIntValue(): Int {
        var value = 0
        val cleanString: String = text.toString().trim { it <= ' ' }
            .replace("[$,.]".toRegex(), "").replace(symbol.toRegex(), "")
            .replace("\\s+".toRegex(), "")
        try {
            value = cleanString.toInt()
        } catch (e: java.lang.NumberFormatException) {
        }

        return value
    }


}